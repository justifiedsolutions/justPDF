package com.justifiedsolutions.justpdf.api.content;

/**
 * Interface that marks {@link Content} as being able to be kept together. This means that the layout engine will
 * attempt to keep the content together and not break it across page boundaries. However, this is just a suggestion. If
 * the content is longer than a page long, it will have to be broken up. However, it will be guaranteed to start on a
 * new page.
 */
public interface KeepTogetherCapable extends Content {

    /**
     * Specifies whether the Content should be kept together on the same page. The default value is {@code false}.
     *
     * @return true if the content should be kept together on the same page
     */
    boolean isKeepTogether();

    /**
     * Specifies whether the entire Content should be kept together on the same page.
     *
     * @param keepTogether true if the content should be kept together
     */
    void setKeepTogether(boolean keepTogether);
}
