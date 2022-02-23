package com.lctrans.livecamtranslator;

public class Languages {
    private static String[] langCodesEN = {"af", "sq", "am", "ar", "hy", "az", "ba", "eu", "be", "bn", "bs", "bg", "ca", "ceb", "zh", "hr", "cs", "da", "nl", "en", "eo", "et", "fi", "fr", "gl", "ka", "de", "el", "gu", "ht", "he", "mrj", "hi", "hu", "is", "id", "ga", "it", "ja", "jv", "kn", "kk", "ko", "ky", "la", "lv", "lt", "lb", "mk", "mg", "ms", "ml", "mt", "mi", "mr", "mhr", "mn", "ne", "no", "pap", "fa", "pl", "pt", "pa", "ro", "ru", "gd", "sr", "si", "sk", "sl", "es", "xh", "su", "sw", "sv", "tl", "tg", "ta", "tt", "te", "th", "tr", "udm", "uk", "ur", "uz", "vi", "cy", "yi"};
    private static String[] langsEN = {"Afrikaans", "Albanian", "Amharic", "Arabic", "Armenian", "Azerbaijan", "Bashkir", "Basque", "Belarussian", "Bengali", "Bosnian", "Bulgarian", "Catalan", "Cebuano", "Chinese", "Croatian", "Czech", "Danish", "Dutch", "English", "Esperanto", "Estonian", "Finnish", "French", "Galician", "Georgian", "German", "Greek", "Gujarati", "Haitian", "Hebrew", "Hill Mari", "Hindi", "Hungarian", "Icelandic", "Indonesian", "Irish", "Italian", "Japanese", "Javanese", "Kannada", "Kazakh", "Korean", "Kyrgyz", "Latin", "Latvian", "Lithuanian", "Luxembourg", "Macedonian", "Malagasy", "Malay", "Malayalam", "Maltese", "Maori", "Marathi", "Mari", "Mongolian", "Nepali", "Norwegian", "Papiamento", "Persian", "Polish", "Portuguese", "Punjabi", "Romanian", "Russian", "Scottish", "Serbian", "Sinhala", "Slovak", "Slovenian", "Spanish", "Spit", "Sundanese", "Swahili", "Swedish", "Tagalog", "Tajik", "Tamil", "Tatar", "Telugu", "Thai", "Turkish", "Udmurt", "Ukrainian", "Urdu", "Uzbek", "Vietnamese", "Welsh", "Yiddish"};
    private static String[] speakLang = {"Afrikaans", "Amharic", "Arabic", "Armenian", "Azerbaijan", "Basque", "Bengali", "Bulgarian", "Catalan", "Chinese", "Croatian", "Czech", "Danish", "Dutch", "English", "Finnish", "French", "Galician", "Georgian", "German", "Greek", "Gujarati", "Hebrew", "Hindi", "Hungarian", "Icelandic", "Indonesian", "Italian", "Japanese", "Javanese", "Kannada", "Korean", "Latvian", "Lithuanian", "Malay", "Malayalam", "Marathi", "Nepali", "Norwegian", "Persian", "Polish", "Portuguese", "Romanian", "Russian", "Serbian", "Sinhala", "Slovak", "Slovenian", "Spanish", "Sundanese", "Swahili", "Swedish", "Tamil", "Telugu", "Thai", "Turkish", "Ukrainian", "Urdu", "Vietnamese"};
    public static String[] speakLangCode = {"af-ZA", "am-ET", "ar", "hy-AM", "az-AZ", "eu-ES", "bn", "bg-BG", "ca-ES", "zh", "hr-HR", "cs-CZ", "da-DK", "nl-NL", "en", "fi-FI", "fr-FR", "gl-ES", "ka-GE", "de-DE", "el-GR", "gu-IN", "he-IL", "hi-IN", "hu-HU", "is-IS", "id-ID", "it-IT", "ja-JP", "jv-ID", "kn-IN", "ko-KR", "lv-LV", "lt-LT", "ms-MY", "ml-IN", "mr-IN", "ne-NP", "nb-NO", "fa-IR", "pl-PL", "pt", "ro-RO", "ru-RU", "sr-RS", "si-LK", "sk-SK", "sl-SI", "es", "su-ID", "sw", "sv-SE", "ta", "te-IN", "th-TH", "tr-TR", "uk-UA", "ur-IN", "vi-VN"};
    private static String[] speechCode = {"ko", "fr", "ru", "zh", "hu", "th", "nb", "da", "tr", "bs", "sw", "pt", "vi", "en", "sv", "bn", "sk", "el", "hi", "fi", "bn", "km", "uk", "nl", "sr", "de", "si", "ku", "cs", "pl", "fil", "it", "ne", "hr", "es", "cy", "ja", "sq", "yue", "la", "in", "ca", "ta"};
    private static String[] sptrCode = {"af", "am", "ar", "hy", "az", "eu", "bn", "bg", "ca", "zh", "hr", "cs", "da", "nl", "en", "fi", "fr", "gl", "ka", "de", "el", "gu", "he", "hi", "hu", "is", "id", "it", "ja", "jv", "kn", "ko", "lv", "lt", "ms", "ml", "mr", "ne", "no", "fa", "pl", "pt", "ro", "ru", "sr", "si", "sk", "sl", "es", "su", "sw", "sv", "ta", "te", "th", "tr", "uk", "ur", "vi"};

    public static String[] getLangsEN() {
        return langsEN;
    }

    public static String getLangCodeEN(int i) {
        return langCodesEN[i];
    }

    public static String[] getSpeakLang() {
        return speakLang;
    }

    public static String getsptrCode(int i) {
        return sptrCode[i];
    }

    public static String getSpeakLangCode(int i) {
        return speakLangCode[i];
    }

    public static String[] getSpeechCode() {
        return speechCode;
    }
}
