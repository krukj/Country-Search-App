package org.example.country;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Country {
    private Name name;
    private String[] tld; // domain name, ex. .pl
    private String cca2; // country code
    private String ccn3; // country code
    private String cca3; // country code
    private String cioc; // country code
    private boolean independent;  // independence status (the country is considered a sovereign state)
    private String status;
    private boolean unMember; // united nations
    private Map<String, Currency> currencies;
    private IDD idd; // calling code
    private String[] capital; // list of capital cities of the country
    private String[] altSpellings; // alternative spellings
    private String region;
    private String subregion;
    private Map<String, String> languages;  // <languageAbbreviation, languageName>
    private Map<String, Translation> translations;
    private double[] latlng; // latitude, longitude
    private boolean landlocked; // almost or entirely surrounded by land
    private String[] borders;
    private int area;
    private Demonyms demonyms;  // gendered inhabitants of the country
    private String flag; // weird format - link to the svg flag
    private Maps maps;
    private int population;
    private String fifa;  // fifa code
    private Car car;
    private String[] timezones;  // we can convert this later to java time zone classes
    private List<String> continents;  // list of continents the country is on
    private Map<String, String> flags;  // key - svg, png, alt
    private CoatOfArms coatOfArms;  // links to svg and png images
    private String startOfWeek;
    private CapitalInfo capitalInfo;  // lat and long of the capital cities
    private Map<String, Double> gini;  // world bank gini index
    private PostalCode postalCode;

    public static class Name {
        private String common;
        private String official;
        private Map<String, NativeName> nativeName;

        public String getCommon() {
            return common;
        }

        public String getOfficial() {
            return official;
        }

        public Map<String, NativeName> getNativeName() {
            return nativeName;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "common='" + common + '\'' +
                    ", official='" + official + '\'' +
                    ", nativeName=" + nativeName +
                    '}';
        }
    }

    public static class NativeName {
        private String common;
        private String official;

        public String getCommon() {
            return common;
        }

        public String getOfficial() {
            return official;
        }

        @Override
        public String toString() {
            return "NativeName{" +
                    "common='" + common + '\'' +
                    ", official='" + official + '\'' +
                    '}';
        }
    }

    public static class Currency {
        private String name;
        private String symbol;

        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }

        @Override
        public String toString() {
            return "Currency{" +
                    "name='" + name + '\'' +
                    ", symbol='" + symbol + '\'' +
                    '}';
        }
    }
    public static class IDD {
        private String root;
        private String[] suffixes;

        public String getRoot() {
            return root;
        }

        public String[] getSuffixes() {
            return suffixes;
        }

        @Override
        public String toString() {
            return "IDD{" +
                    "root='" + root + '\'' +
                    ", suffixes=" + Arrays.toString(suffixes) +
                    '}';
        }
    }

    public static class Translation {
        private String common;
        private String official;

        public String getCommon() {
            return common;
        }

        public String getOfficial() {
            return official;
        }

        @Override
        public String toString() {
            return "Translation{" +
                    "common='" + common + '\'' +
                    ", official='" + official + '\'' +
                    '}';
        }
    }


    public static class Demonyms {
        private Map<String, GenderedDemonyms> genderedDemonyms;

        public Map<String, GenderedDemonyms> getGenderedDemonyms() {
            return genderedDemonyms;
        }

        @Override
        public String toString() {
            return "Demonyms{" +
                    "genderedDemonyms=" + genderedDemonyms +
                    '}';
        }
    }

    public static class GenderedDemonyms {
        private String f; // female
        private String m; // male

        public String getF() {
            return f;
        }

        public String getM() {
            return m;
        }

        @Override
        public String toString() {
            return "GenderedDemonyms{" +
                    "f='" + f + '\'' +
                    ", m='" + m + '\'' +
                    '}';
        }
    }

    public static class Maps {
        private String googleMaps;
        private String openStreetMaps;

        public String getGoogleMaps() {
            return googleMaps;
        }

        public String getOpenStreetMaps() {
            return openStreetMaps;
        }

        @Override
        public String toString() {
            return "Maps{" +
                    "googleMaps='" + googleMaps + '\'' +
                    ", openStreetMaps='" + openStreetMaps + '\'' +
                    '}';
        }
    }

    public static class Car {
        private String[] signs;
        private String side;

        public String[] getSigns() {
            return signs;
        }

        public String getSide() {
            return side;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "signs=" + Arrays.toString(signs) +
                    ", side='" + side + '\'' +
                    '}';
        }
    }

    public static class CapitalInfo {
        private double[] latlng;

        public double[] getLatlng() {
            return latlng;
        }

        @Override
        public String toString() {
            return "CapitalInfo{" +
                    "latlng=" + Arrays.toString(latlng) +
                    '}';
        }
    }

    public static class PostalCode {
        private String format;
        private String regex;

        public String getFormat() {
            return format;
        }

        public String getRegex() {
            return regex;
        }

        @Override
        public String toString() {
            return "PostalCode{" +
                    "format='" + format + '\'' +
                    ", regex='" + regex + '\'' +
                    '}';
        }
    }

    public static class CoatOfArms {
        private String png;
        private String svg;

        public String getPng() {
            return png;
        }

        public String getSvg() {
            return svg;
        }

        @Override
        public String toString() {
            return "CoatOfArms{" +
                    "png='" + png + '\'' +
                    ", svg='" + svg + '\'' +
                    '}';
        }
    }

    public Name getName() {
        return name;
    }

    public String[] getTld() {
        return tld;
    }

    public String getCca2() {
        return cca2;
    }

    public String getCcn3() {
        return ccn3;
    }

    public String getCca3() {
        return cca3;
    }

    public String getCioc() {
        return cioc;
    }

    public boolean isIndependent() {
        return independent;
    }

    public String getStatus() {
        return status;
    }

    public boolean isUnMember() {
        return unMember;
    }

    public Map<String, Currency> getCurrencies() {
        return currencies;
    }

    public String[] getCapital() {
        return capital;
    }

    public String[] getBorders() {
        return borders;
    }

    public String[] getTimezones() {
        return timezones;
    }

    public String getStartOfWeek() {
        return startOfWeek;
    }

    public IDD getIdd() {
        return idd;
    }

    public String[] getCapitals() {
        return capital;
    }

    public String[] getAltSpellings() {
        return altSpellings;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }


    public double[] getLatlng() {
        return latlng;
    }

    public boolean isLandlocked() {
        return landlocked;
    }


    public int getArea() {
        return area;
    }

    public Map<String, Translation> getTranslations() {
        return translations;
    }

    public Map<String, Double> getGini() {
        return gini;
    }

    public Demonyms getDemonyms() {
        return demonyms;
    }

    public String getFlag() {
        return flag;
    }

    public Maps getMaps() {
        return maps;
    }

    public int getPopulation() {
        return population;
    }

    public String getFifa() {
        return fifa;
    }

    public Car getCar() {
        return car;
    }

    public String[] getTimeZone() {
        return timezones;
    }

    public List<String> getContinents() {
        return continents;
    }

    public Map<String, String> getFlags() {
        return flags;
    }

    public CoatOfArms getCoatOfArms() {
        return coatOfArms;
    }

    public String getStartOfTheWeek() {
        return startOfWeek;
    }

    public CapitalInfo getCapitalInfo() {
        return capitalInfo;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name=" + name +
                ", tld=" + Arrays.toString(tld) +
                ", cca2='" + cca2 + '\'' +
                ", ccn3='" + ccn3 + '\'' +
                ", cca3='" + cca3 + '\'' +
                ", cioc='" + cioc + '\'' +
                ", independent=" + independent +
                ", status='" + status + '\'' +
                ", unMember=" + unMember +
                ", currencies=" + currencies +
                ", idd=" + idd +
                ", capital=" + Arrays.toString(capital) +
                ", altSpellings=" + Arrays.toString(altSpellings) +
                ", region='" + region + '\'' +
                ", subregion='" + subregion + '\'' +
                ", languages=" + languages +
                ", translations=" + translations +
                ", latlng=" + Arrays.toString(latlng) +
                ", landlocked=" + landlocked +
                ", borders=" + Arrays.toString(borders) +
                ", area=" + area +
                ", demonyms=" + demonyms +
                ", flag='" + flag + '\'' +
                ", maps=" + maps +
                ", population=" + population +
                ", fifa='" + fifa + '\'' +
                ", car=" + car +
                ", timezones=" + Arrays.toString(timezones) +
                ", continents=" + continents +
                ", flags=" + flags +
                ", coatOfArms=" + coatOfArms +
                ", startOfWeek='" + startOfWeek + '\'' +
                ", capitalInfo=" + capitalInfo +
                ", gini=" + gini +
                ", postalCode=" + postalCode +
                '}';
    }
}


