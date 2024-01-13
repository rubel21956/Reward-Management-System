package net.spectrum.api.util.constants;

public final class ValidationPattern {
    public static final String requestTimePattern = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
    public static final String attachmentTypeForStepOne = "এজেন্টদের ঘোষণা মোতাবেক বিল অফ এন্ট্রি";
    public static final String attachmentTypeForStepFive = "সাক্ষরিত আবেদনপত্র";
}
