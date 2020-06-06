package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.pdf.font.PDFFontType1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class does the heavy lifting of implementing the Knuth-Liang hyphenation algorithm. The hyphen index arrays
 * returned by {@link #hyphenate(String)} identify the index of the character that would follow the hyphen. Example: if
 * the hyphenation of {@code fubar} was {@code fu-bar}, the array would be {@code [2]}.
 */
final class HyphenProcessor {
    private static final Map<String, String> EXCEPTIONS = new HashMap<>();
    private static final Map<String, String> PATTERNS = new HashMap<>();

    static {
        initExceptions();
        initPatterns();
    }

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
        String match = replaceDots(pattern);

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

    private static String replaceDots(String pattern) {
        String result = pattern;
        if (result.startsWith(".")) {
            result = result.replace(".", "^");
            result = result + ".+";
        } else if (result.endsWith(".")) {
            result = result.replace(".", "$");
            result = ".*" + result;
        }
        return result;
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
     * Determines the possible hyphenation points for the specified text.
     *
     * @param text the text to hyphenate
     * @return the array of hyphenation points for the specified text. Empty if there is none.
     */
    int[] hyphenate(String text) {
        int[] result = processExceptions(text);
        if (result == null) {
            result = processPatterns(text);
        }
        return result;
    }

    private int[] processExceptions(String text) {
        for (Map.Entry<String, String> entry : EXCEPTIONS.entrySet()) {
            if (Objects.equals(entry.getValue(), text)) {
                List<Integer> indexes = new ArrayList<>();
                String key = entry.getKey();
                while (key.contains("-")) {
                    indexes.add(key.indexOf('-'));
                    key = key.replaceFirst("-", "");
                }
                return toIntArray(indexes);
            }
        }
        return null;
    }

    private int[] processPatterns(String text) {
        int[] hyphenMap = new int[text.length()];

        for (Map.Entry<String, String> entry : PATTERNS.entrySet()) {
            String hyphenPattern = entry.getKey();
            String regex = entry.getValue();
            if (text.matches(regex)) {
                processPattern(text, hyphenPattern, regex, hyphenMap);
            }
        }

        return createHyphenArray(hyphenMap);
    }

    private void processPattern(String text, String pattern, String regex, int[] hyphenMap) {
        Matcher matcher = Pattern.compile(regex.replaceAll("[.*+]", "")).matcher(text);
        List<HyphenDescriptor> descriptors = getHyphenIndexesFromPattern(pattern.replace(".", ""));
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

    private int[] createHyphenArray(int[] hyphenMap) {
        List<Integer> locs = new ArrayList<>();
        //ignore first two and the last indexes on purpose
        for (int i = 2; i < hyphenMap.length - 1; i++) {
            int hyphen = hyphenMap[i];
            if (hyphen % 2 == 1) {
                locs.add(i);
            }
        }
        return toIntArray(locs);
    }

    /**
     * Identifies a hyphen point based on a Liang hyphen pattern.
     */
    private static class HyphenDescriptor {
        private int textIndex;
        private int value;
    }

}
