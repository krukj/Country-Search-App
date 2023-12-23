package org.example.country;

import java.util.Map;

public class Country {
    private Name name;
    private String tld; // domain name, ex. .pl
    private String cca2; // country code
    private String ccn3; // country code
    private String cca3; // country code
    private String cioc; // country code
    private boolean independent;
    private Status status;
    private boolean unMember; // united nations
    private Currency currency;
    private IDD idd; // calling code


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
}


