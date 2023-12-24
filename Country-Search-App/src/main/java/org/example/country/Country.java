package org.example.country;

import java.util.Map;

public class Country {
    private Name name;
    private String tld; // domain name, ex. .pl
    private String cca2; // country code
    private String ccn3; // country code
    private String cca3; // country code
    private String cioc; // country code
    private boolean independent;  // independence status (the country is considered a sovereign state)
    private Status status;
    private boolean unMember; // united nations
    private Currency currency;
    private IDD idd; // calling code
    private List<String> capitals; // list of capital cities of the country
    private List<String> altSpellings; // alternative spellings
    private String region;
    private String subregion;
    private Map<String, String> languages;  // <languageAbbreviation, languageName>
    private Translation translation;
    private double[] latLang; // always 2 values
    private boolean landlocked; // almost or entirely surrounded by land
    private List<String> boarders;
    private int area;
    private String demonym;  // inhabitants of the country
    private Demonyms demonyms;  // genderized inhabitants of the country
    private String flag; // weird format - link to the svg flag
    private Maps maps;
    private int population;
    private String fifa;  // fifa code
    private Car car;
    private String timeZone;  // we can convert this later to java time zone classes
    private List<String> continents;  // list of continents the country is on
    private String[] flagPics;  // links to svg and png flags - exactly 2 values
    private String flagDesc;  // flag descpription
    private String[] coatOfArms;  // links to svg and png images - exactly 2 values
    private String startOfTheWeek;
    private CapitalInfo capitalInfo;  // lat and lang of the capital cities
    private Gini gini;  // worldbank gini index
    private PostalCode postalCode;

    public static class Name {
        private String common;
        private String official;
        private Map<String, NativeName> nativeName;
    }

    public static class NativeName {
        private String common;
        private String official;

    }
    public enum Status{
        officially_assigned, user_assigned
    }
    public static class Currency {
        private String name;
        private String symbol;
    }
    public static class IDD {
        private String root;
        private String suffixes;
    }

    public static class Translation {
        private String common;
        private String official;
        private Map<String, TranslationName> translationName;
    }

    public static class TranslationName {
        private String common;
        private String official;
    }

    public static class Demonyms {
        private String[] names;  // always 2 values - one for males and one for females
        private Map<String, names> demonymsNames; // <language, names>
    }

    public static class Maps {
        private String googleMaps;
        private String openStreetMaps;
    }

    public static class Car {
        private String sign;
        private Side side;

        public enum Side {
            left, right
        }
    }

    public static class CapitalInfo {
        private double[] capitalLatLang;
        private Map<String, capitalLatLang> capitalInfoMap;
    }

    public static class Gini {
        private Map<String, double> giniIndex;  // <mostRecentYear, mostRecentValue>
    }

    public static class PostalCode {
        private String format;
        private String regex;
    }

}


