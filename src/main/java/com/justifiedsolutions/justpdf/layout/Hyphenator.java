package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.pdf.font.PDFFontType1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Hyphenator {
    static final int DONE = Integer.MIN_VALUE;

    private static final Map<String, String> EXCEPTIONS = new HashMap<>();
    private static final Map<String, String> PATTERNS = new HashMap<>();

    static {
        initExceptions();
        initPatterns();
    }

    private String text;
    private int[] hyphens = {};
    private int index = DONE;

    private static void initPatterns() {
        String location = "/hyphen/patterns.list";
        InputStream is = PDFFontType1.class.getResourceAsStream(location);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.US_ASCII))) {
            String line = reader.readLine();
            while (line != null) {
                processPattern(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read patterns file: " + location, e);
        }
    }

    private static void processPattern(String pattern) {
        String match = pattern;
        if (match.startsWith(".")) {
            match = match.replace(".", "^");
            match = match + ".+";
        } else if (match.endsWith(".")) {
            match = match.replace(".", "$");
            match = ".*" + match;
        }

        if (Character.isDigit(match.charAt(0))) {
            match = ".+" + match;
        }
        if (Character.isDigit(match.charAt(match.length() - 1))) {
            match = match + ".+";
        }
        if (!match.startsWith(".") && !match.startsWith("^")) {
            match = ".*" + match;
        }
        if (!match.endsWith("$") && !match.endsWith("*") && !match.endsWith("+")) {
            match = match + ".*";
        }

        match = match.replaceAll("[1-5]", "");
        PATTERNS.put(pattern, match);
    }

    private static void initExceptions() {
        String location = "/hyphen/exceptions.list";
        InputStream is = PDFFontType1.class.getResourceAsStream(location);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.US_ASCII))) {
            String line = reader.readLine();
            while (line != null) {
                String match = line.replace("-", "");
                EXCEPTIONS.put(line, match);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read exceptions file: " + location, e);
        }
    }

    /**
     * Set a new text string to be scanned. The current scan position is reset to first().
     *
     * @param text new text to scan
     * @throws NullPointerException if text is {@code null}
     */
    void setText(String text) {
        this.text = Objects.requireNonNull(text);
        hyphens = new int[]{};
        index = DONE;
        if (processExceptions()) {
            first();
            return;
        }
        processPatterns();
        first();
    }

    /**
     * Returns the first hyphen. The iterator's current position is set to the first hyphen.
     *
     * @return The character index of the first hyphen
     */
    int first() {
        if (hyphens.length > 0) {
            index = 0;
            return hyphens[index];
        }
        index = DONE;
        return DONE;
    }

    /**
     * Returns the last hyphen. The iterator's current position is set to the last hyphen.
     *
     * @return The character index of the last hyphen
     */
    int last() {
        if (hyphens.length > 0) {
            index = (hyphens.length - 1);
            return hyphens[index];
        }
        index = DONE;
        return DONE;
    }

    /**
     * Returns the hyphen following the current hyphen. If the current hyphen is the last hyphen, it returns
     * Hyphenator.DONE and the iterator's current position is unchanged. Otherwise, the iterator's current position is
     * set to the hyphen following the current hyphen.
     *
     * @return The character index of the next hyphen or {@code Hyphenator.DONE} if the current boundary is the last
     * text boundary.
     */
    int next() {
        if (index == (hyphens.length - 1) || index == DONE) {
            return DONE;
        }

        index++;
        if (index < hyphens.length) {
            return hyphens[index];
        }
        return DONE;
    }

    /**
     * Returns the hyphen preceding the current hyphen. If the current hyphen is the first hyphen, it returns
     * Hyphenator.DONE and the iterator's current position is unchanged. Otherwise, the iterator's current position is
     * set to the hyphen preceding the current hyphen.
     *
     * @return The character index of the previous hyphen or Hyphenator.DONE if the current hyphen is the first hyphen.
     */
    int previous() {
        if (index == 0 || index == DONE) {
            return DONE;
        }

        index--;
        if (index >= 0) {
            return hyphens[index];
        }
        return DONE;
    }

    /**
     * Returns an array of the indexes of all of the hyphens.
     *
     * @return the indexes of all of the hyphens
     */
    int[] all() {
        return Arrays.copyOf(hyphens, hyphens.length);
    }

    private boolean processExceptions() {
        for (Map.Entry<String, String> entry : EXCEPTIONS.entrySet()) {
            if (Objects.equals(entry.getValue(), text)) {
                List<Integer> indexes = new ArrayList<>();
                String key = entry.getKey();
                while (key.contains("-")) {
                    indexes.add(key.indexOf("-"));
                    key = key.replaceFirst("-", "");
                }
                hyphens = toIntArray(indexes);
                return true;
            }
        }
        return false;
    }

    private void processPatterns() {
        int[] hyphenMap = new int[text.length()];

        for (Map.Entry<String, String> entry : PATTERNS.entrySet()) {
            String hyphenPattern = entry.getKey();
            String regex = entry.getValue();
            if (text.matches(regex)) {
                processPattern(hyphenPattern, regex, hyphenMap);
            }
        }

        createHyphenArray(hyphenMap);
    }

    private void processPattern(String pattern, String regex, int[] hyphenMap) {
        regex = regex.replaceAll("[.*^$+]", "");
        Matcher matcher = Pattern.compile(regex).matcher(text);
        pattern = pattern.replace(".", "");
        List<HyphenDescriptor> descriptors = getHyphenIndexesFromPattern(pattern);
        while (matcher.find()) {
            int start = matcher.start();
            for (HyphenDescriptor descriptor : descriptors) {
                int tmpIndex = start + descriptor.textIndex;
                hyphenMap[tmpIndex] = Math.max(hyphenMap[tmpIndex], descriptor.value);
            }
        }
    }

    private List<HyphenDescriptor> getHyphenIndexesFromPattern(String pattern) {
        Matcher matcher = Pattern.compile("[1-5]").matcher(pattern);
        List<HyphenDescriptor> list = new ArrayList<>();
        while (matcher.find()) {
            HyphenDescriptor descriptor = new HyphenDescriptor();
            descriptor.textIndex = matcher.start() - list.size();
            descriptor.value = Character.getNumericValue(pattern.charAt(matcher.start()));
            list.add(descriptor);
        }
        return list;
    }

    private int[] toIntArray(List<Integer> ints) {
        int[] result = new int[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            result[i] = ints.get(i);
        }
        return result;
    }

    private void createHyphenArray(int[] hyphenMap) {
        List<Integer> locs = new ArrayList<>();
        //ignore first two and the last indexes on purpose
        for (int i = 2; i < hyphenMap.length - 1; i++) {
            int hyphen = hyphenMap[i];
            if (hyphen % 2 == 1) {
                locs.add(i);
            }
        }
        hyphens = toIntArray(locs);
    }

    private static class HyphenDescriptor {
        private int textIndex;
        private int value;
    }
}
