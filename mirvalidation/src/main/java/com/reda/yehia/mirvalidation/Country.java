package com.reda.yehia.mirvalidation;

/**
 * Created by yehia on 27/05/19.
 */


import java.util.ArrayList;
import java.util.List;

public class Country {

    public List<Country> COUNTRIES = new ArrayList<>();
    private String code;
    private String name;
    private String dialCode;
    private int flag = -1;
    private int length_min = 0;
    private int length_max = 1;

    public static List<Country> allCountriesList;

    public Country(String code, String name, String dialCode, int length_min, int length_max) {
        this.code = code;
        this.name = name;
        this.dialCode = dialCode;
        this.length_min = length_min;
        this.length_max = length_max;
    }

    public Country() {

        COUNTRIES.add(new Country("AD", "Andorra", "+376", 0, 1));
        COUNTRIES.add(new Country("AE", "United Arab Emirates", "+971", 0, 1));
        COUNTRIES.add(new Country("AF", "Afghanistan", "+93", 0, 1));
        COUNTRIES.add(new Country("AG", "Antigua and Barbuda", "+1", 0, 1));
        COUNTRIES.add(new Country("AI", "Anguilla", "+1", 0, 1));
        COUNTRIES.add(new Country("AL", "Albania", "+355", 0, 1));
        COUNTRIES.add(new Country("AM", "Armenia", "+374", 0, 1));
        COUNTRIES.add(new Country("AO", "Angola", "+244", 0, 1));
        COUNTRIES.add(new Country("AQ", "Antarctica", "+672", 0, 1));
        COUNTRIES.add(new Country("AR", "Argentina", "+54", 0, 1));
        COUNTRIES.add(new Country("AS", "AmericanSamoa", "+1", 0, 1));
        COUNTRIES.add(new Country("AT", "Austria", "+43", 0, 1));
        COUNTRIES.add(new Country("AU", "Australia", "+61", 0, 1));
        COUNTRIES.add(new Country("AW", "Aruba", "+297", 0, 1));
        COUNTRIES.add(new Country("AX", "Åland Islands", "+358", 0, 1));
        COUNTRIES.add(new Country("AZ", "Azerbaijan", "+994", 0, 1));
        COUNTRIES.add(new Country("BA", "Bosnia and Herzegovina", "+387", 0, 1));
        COUNTRIES.add(new Country("BB", "Barbados", "+1", 0, 1));
        COUNTRIES.add(new Country("BD", "Bangladesh", "+880", 0, 1));
        COUNTRIES.add(new Country("BE", "Belgium", "+32", 0, 1));
        COUNTRIES.add(new Country("BF", "Burkina Faso", "+226", 0, 1));
        COUNTRIES.add(new Country("BG", "Bulgaria", "+359", 0, 1));
        COUNTRIES.add(new Country("BH", "Bahrain", "+973", 0, 1));
        COUNTRIES.add(new Country("BI", "Burundi", "+257", 0, 1));
        COUNTRIES.add(new Country("BJ", "Benin", "+229", 0, 1));
        COUNTRIES.add(new Country("BL", "Saint Barthélemy", "+590", 0, 1));
        COUNTRIES.add(new Country("BM", "Bermuda", "+1", 0, 1));
        COUNTRIES.add(new Country("BN", "Brunei Darussalam", "+673", 0, 1));
        COUNTRIES.add(new Country("BO", "Bolivia, Plurinational State of", "+591", 0, 1));
        COUNTRIES.add(new Country("BQ", "Bonaire", "+599", 0, 1));
        COUNTRIES.add(new Country("BR", "Brazil", "+55", 0, 1));
        COUNTRIES.add(new Country("BS", "Bahamas", "+1", 0, 1));
        COUNTRIES.add(new Country("BT", "Bhutan", "+975", 0, 1));
        COUNTRIES.add(new Country("BV", "Bouvet Island", "+47", 0, 1));
        COUNTRIES.add(new Country("BW", "Botswana", "+267", 0, 1));
        COUNTRIES.add(new Country("BY", "Belarus", "+375", 0, 1));
        COUNTRIES.add(new Country("BZ", "Belize", "+501", 0, 1));
        COUNTRIES.add(new Country("CA", "Canada", "+1", 0, 1));
        COUNTRIES.add(new Country("CC", "Cocos (Keeling) Islands", "+61", 0, 1));
        COUNTRIES.add(new Country("CD", "Congo, The Democratic Republic of the", "+243", 0, 1));
        COUNTRIES.add(new Country("CF", "Central African Republic", "+236", 0, 1));
        COUNTRIES.add(new Country("CG", "Congo", "+242", 0, 1));
        COUNTRIES.add(new Country("CH", "Switzerland", "+41", 0, 1));
        COUNTRIES.add(new Country("CI", "Ivory Coast", "+225", 0, 1));
        COUNTRIES.add(new Country("CK", "Cook Islands", "+682", 0, 1));
        COUNTRIES.add(new Country("CL", "Chile", "+56", 0, 1));
        COUNTRIES.add(new Country("CM", "Cameroon", "+237", 0, 1));
        COUNTRIES.add(new Country("CN", "China", "+86", 0, 1));
        COUNTRIES.add(new Country("CO", "Colombia", "+57", 0, 1));
        COUNTRIES.add(new Country("CR", "Costa Rica", "+506", 0, 1));
        COUNTRIES.add(new Country("CU", "Cuba", "+53", 0, 1));
        COUNTRIES.add(new Country("CV", "Cape Verde", "+238", 0, 1));
        COUNTRIES.add(new Country("CW", "Curacao", "+599", 0, 1));
        COUNTRIES.add(new Country("CX", "Christmas Island", "+61", 0, 1));
        COUNTRIES.add(new Country("CY", "Cyprus", "+357", 0, 1));
        COUNTRIES.add(new Country("CZ", "Czech Republic", "+420", 0, 1));
        COUNTRIES.add(new Country("DE", "Germany", "+49", 0, 1));
        COUNTRIES.add(new Country("DJ", "Djibouti", "+253", 0, 1));
        COUNTRIES.add(new Country("DK", "Denmark", "+45", 0, 1));
        COUNTRIES.add(new Country("DM", "Dominica", "+1", 0, 1));
        COUNTRIES.add(new Country("DO", "Dominican Republic", "+1", 0, 1));
        COUNTRIES.add(new Country("DZ", "Algeria", "+213", 0, 1));
        COUNTRIES.add(new Country("EC", "Ecuador", "+593", 0, 1));
        COUNTRIES.add(new Country("EE", "Estonia", "+372", 0, 1));
        COUNTRIES.add(new Country("EG", "Egypt", "+20", 11, 12));
        COUNTRIES.add(new Country("EH", "Western Sahara", "+212", 0, 1));
        COUNTRIES.add(new Country("ER", "Eritrea", "+291", 0, 1));
        COUNTRIES.add(new Country("ES", "Spain", "+34", 0, 1));
        COUNTRIES.add(new Country("ET", "Ethiopia", "+251", 0, 1));
        COUNTRIES.add(new Country("FI", "Finland", "+358", 0, 1));
        COUNTRIES.add(new Country("FJ", "Fiji", "+679", 0, 1));
        COUNTRIES.add(new Country("FK", "Falkland Islands (Malvinas)", "+500", 0, 1));
        COUNTRIES.add(new Country("FM", "Micronesia, Federated States of", "+691", 0, 1));
        COUNTRIES.add(new Country("FO", "Faroe Islands", "+298", 0, 1));
        COUNTRIES.add(new Country("FR", "France", "+33", 0, 1));
        COUNTRIES.add(new Country("GA", "Gabon", "+241", 0, 1));
        COUNTRIES.add(new Country("GB", "United Kingdom", "+44", 0, 1));
        COUNTRIES.add(new Country("GD", "Grenada", "+1", 0, 1));
        COUNTRIES.add(new Country("GE", "Georgia", "+995", 0, 1));
        COUNTRIES.add(new Country("GF", "French Guiana", "+594", 0, 1));
        COUNTRIES.add(new Country("GG", "Guernsey", "+44", 0, 1));
        COUNTRIES.add(new Country("GH", "Ghana", "+233", 0, 1));
        COUNTRIES.add(new Country("GI", "Gibraltar", "+350", 0, 1));
        COUNTRIES.add(new Country("GL", "Greenland", "+299", 0, 1));
        COUNTRIES.add(new Country("GM", "Gambia", "+220", 0, 1));
        COUNTRIES.add(new Country("GN", "Guinea", "+224", 0, 1));
        COUNTRIES.add(new Country("GP", "Guadeloupe", "+590", 0, 1));
        COUNTRIES.add(new Country("GQ", "Equatorial Guinea", "+240", 0, 1));
        COUNTRIES.add(new Country("GR", "Greece", "+30", 0, 1));
        COUNTRIES.add(new Country("GS", "South Georgia and the South Sandwich Islands", "+500", 0, 1));
        COUNTRIES.add(new Country("GT", "Guatemala", "+502", 0, 1));
        COUNTRIES.add(new Country("GU", "Guam", "+1", 0, 1));
        COUNTRIES.add(new Country("GW", "Guinea-Bissau", "+245", 0, 1));
        COUNTRIES.add(new Country("GY", "Guyana", "+595", 0, 1));
        COUNTRIES.add(new Country("HK", "Hong Kong", "+852", 0, 1));
        COUNTRIES.add(new Country("HM", "Heard Island and McDonald Islands", "", 0, 1));
        COUNTRIES.add(new Country("HN", "Honduras", "+504", 0, 1));
        COUNTRIES.add(new Country("HR", "Croatia", "+385", 0, 1));
        COUNTRIES.add(new Country("HT", "Haiti", "+509", 0, 1));
        COUNTRIES.add(new Country("HU", "Hungary", "+36", 0, 1));
        COUNTRIES.add(new Country("ID", "Indonesia", "+62", 0, 1));
        COUNTRIES.add(new Country("IE", "Ireland", "+353", 0, 1));
        COUNTRIES.add(new Country("IL", "Israel", "+972", 0, 1));
        COUNTRIES.add(new Country("IM", "Isle of Man", "+44", 0, 1));
        COUNTRIES.add(new Country("IN", "India", "+91", 0, 1));
        COUNTRIES.add(new Country("IO", "British Indian Ocean Territory", "+246", 0, 1));
        COUNTRIES.add(new Country("IQ", "Iraq", "+964", 0, 1));
        COUNTRIES.add(new Country("IR", "Iran, Islamic Republic of", "+98", 0, 1));
        COUNTRIES.add(new Country("IS", "Iceland", "+354", 0, 1));
        COUNTRIES.add(new Country("IT", "Italy", "+39", 0, 1));
        COUNTRIES.add(new Country("JE", "Jersey", "+44", 0, 1));
        COUNTRIES.add(new Country("JM", "Jamaica", "+1", 0, 1));
        COUNTRIES.add(new Country("JO", "Jordan", "+962", 0, 1));
        COUNTRIES.add(new Country("JP", "Japan", "+81", 0, 1));
        COUNTRIES.add(new Country("KE", "Kenya", "+254", 0, 1));
        COUNTRIES.add(new Country("KG", "Kyrgyzstan", "+996", 0, 1));
        COUNTRIES.add(new Country("KH", "Cambodia", "+855", 0, 1));
        COUNTRIES.add(new Country("KI", "Kiribati", "+686", 0, 1));
        COUNTRIES.add(new Country("KM", "Comoros", "+269", 0, 1));
        COUNTRIES.add(new Country("KN", "Saint Kitts and Nevis", "+1", 0, 1));
        COUNTRIES.add(new Country("KP", "North Korea", "+850", 0, 1));
        COUNTRIES.add(new Country("KR", "South Korea", "+82", 0, 1));
        COUNTRIES.add(new Country("KW", "Kuwait", "+965", 0, 1));
        COUNTRIES.add(new Country("KY", "Cayman Islands", "+345", 0, 1));
        COUNTRIES.add(new Country("KZ", "Kazakhstan", "+7", 0, 1));
        COUNTRIES.add(new Country("LA", "Lao People\'s Democratic Republic", "+856", 0, 1));
        COUNTRIES.add(new Country("LB", "Lebanon", "+961", 0, 1));
        COUNTRIES.add(new Country("LC", "Saint Lucia", "+1", 0, 1));
        COUNTRIES.add(new Country("LI", "Liechtenstein", "+423", 0, 1));
        COUNTRIES.add(new Country("LK", "Sri Lanka", "+94", 0, 1));
        COUNTRIES.add(new Country("LR", "Liberia", "+231", 0, 1));
        COUNTRIES.add(new Country("LS", "Lesotho", "+266", 0, 1));
        COUNTRIES.add(new Country("LT", "Lithuania", "+370", 0, 1));
        COUNTRIES.add(new Country("LU", "Luxembourg", "+352", 0, 1));
        COUNTRIES.add(new Country("LV", "Latvia", "+371", 0, 1));
        COUNTRIES.add(new Country("LY", "Libyan Arab Jamahiriya", "+218", 0, 1));
        COUNTRIES.add(new Country("MA", "Morocco", "+212", 0, 1));
        COUNTRIES.add(new Country("MC", "Monaco", "+377", 0, 1));
        COUNTRIES.add(new Country("MD", "Moldova, Republic of", "+373", 0, 1));
        COUNTRIES.add(new Country("ME", "Montenegro", "+382", 0, 1));
        COUNTRIES.add(new Country("MF", "Saint Martin", "+590", 0, 1));
        COUNTRIES.add(new Country("MG", "Madagascar", "+261", 0, 1));
        COUNTRIES.add(new Country("MH", "Marshall Islands", "+692", 0, 1));
        COUNTRIES.add(new Country("MK", "Macedonia, The Former Yugoslav Republic of", "+389", 0, 1));
        COUNTRIES.add(new Country("ML", "Mali", "+223", 0, 1));
        COUNTRIES.add(new Country("MM", "Myanmar", "+95", 0, 1));
        COUNTRIES.add(new Country("MN", "Mongolia", "+976", 0, 1));
        COUNTRIES.add(new Country("MO", "Macao", "+853", 0, 1));
        COUNTRIES.add(new Country("MP", "Northern Mariana Islands", "+1", 0, 1));
        COUNTRIES.add(new Country("MQ", "Martinique", "+596", 0, 1));
        COUNTRIES.add(new Country("MR", "Mauritania", "+222", 0, 1));
        COUNTRIES.add(new Country("MS", "Montserrat", "+1", 0, 1));
        COUNTRIES.add(new Country("MT", "Malta", "+356", 0, 1));
        COUNTRIES.add(new Country("MU", "Mauritius", "+230", 0, 1));
        COUNTRIES.add(new Country("MV", "Maldives", "+960", 0, 1));
        COUNTRIES.add(new Country("MW", "Malawi", "+265", 0, 1));
        COUNTRIES.add(new Country("MX", "Mexico", "+52", 0, 1));
        COUNTRIES.add(new Country("MY", "Malaysia", "+60", 0, 1));
        COUNTRIES.add(new Country("MZ", "Mozambique", "+258", 0, 1));
        COUNTRIES.add(new Country("NA", "Namibia", "+264", 0, 1));
        COUNTRIES.add(new Country("NC", "New Caledonia", "+687", 0, 1));
        COUNTRIES.add(new Country("NE", "Niger", "+227", 0, 1));
        COUNTRIES.add(new Country("NF", "Norfolk Island", "+672", 0, 1));
        COUNTRIES.add(new Country("NG", "Nigeria", "+234", 0, 1));
        COUNTRIES.add(new Country("NI", "Nicaragua", "+505", 0, 1));
        COUNTRIES.add(new Country("NL", "Netherlands", "+31", 0, 1));
        COUNTRIES.add(new Country("NO", "Norway", "+47", 0, 1));
        COUNTRIES.add(new Country("NP", "Nepal", "+977", 0, 1));
        COUNTRIES.add(new Country("NR", "Nauru", "+674", 0, 1));
        COUNTRIES.add(new Country("NU", "Niue", "+683", 0, 1));
        COUNTRIES.add(new Country("NZ", "New Zealand", "+64", 0, 1));
        COUNTRIES.add(new Country("OM", "Oman", "+968", 0, 1));
        COUNTRIES.add(new Country("PA", "Panama", "+507", 0, 1));
        COUNTRIES.add(new Country("PE", "Peru", "+51", 0, 1));
        COUNTRIES.add(new Country("PF", "French Polynesia", "+689", 0, 1));
        COUNTRIES.add(new Country("PG", "Papua New Guinea", "+675", 0, 1));
        COUNTRIES.add(new Country("PH", "Philippines", "+63", 0, 1));
        COUNTRIES.add(new Country("PK", "Pakistan", "+92", 0, 1));
        COUNTRIES.add(new Country("PL", "Poland", "+48", 0, 1));
        COUNTRIES.add(new Country("PM", "Saint Pierre and Miquelon", "+508", 0, 1));
        COUNTRIES.add(new Country("PN", "Pitcairn", "+872", 0, 1));
        COUNTRIES.add(new Country("PR", "Puerto Rico", "+1", 0, 1));
        COUNTRIES.add(new Country("PS", "Palestinian Territory, Occupied", "+970", 0, 1));
        COUNTRIES.add(new Country("PT", "Portugal", "+351", 0, 1));
        COUNTRIES.add(new Country("PW", "Palau", "+680", 0, 1));
        COUNTRIES.add(new Country("PY", "Paraguay", "+595", 0, 1));
        COUNTRIES.add(new Country("QA", "Qatar", "+974", 0, 1));
        COUNTRIES.add(new Country("RE", "Réunion", "+262", 0, 1));
        COUNTRIES.add(new Country("RO", "Romania", "+40", 0, 1));
        COUNTRIES.add(new Country("RS", "Serbia", "+381", 0, 1));
        COUNTRIES.add(new Country("RU", "Russia", "+7", 0, 1));
        COUNTRIES.add(new Country("RW", "Rwanda", "+250", 0, 1));
        COUNTRIES.add(new Country("SA", "Saudi Arabia", "+966", 9, 12));
        COUNTRIES.add(new Country("SB", "Solomon Islands", "+677", 0, 1));
        COUNTRIES.add(new Country("SC", "Seychelles", "+248", 0, 1));
        COUNTRIES.add(new Country("SD", "Sudan", "+249", 0, 1));
        COUNTRIES.add(new Country("SE", "Sweden", "+46", 0, 1));
        COUNTRIES.add(new Country("SG", "Singapore", "+65", 0, 1));
        COUNTRIES.add(new Country("SH", "Saint Helena, Ascension and Tristan Da Cunha", "+290", 0, 1));
        COUNTRIES.add(new Country("SI", "Slovenia", "+386", 0, 1));
        COUNTRIES.add(new Country("SJ", "Svalbard and Jan Mayen", "+47", 0, 1));
        COUNTRIES.add(new Country("SK", "Slovakia", "+421", 0, 1));
        COUNTRIES.add(new Country("SL", "Sierra Leone", "+232", 0, 1));
        COUNTRIES.add(new Country("SM", "San Marino", "+378", 0, 1));
        COUNTRIES.add(new Country("SN", "Senegal", "+221", 0, 1));
        COUNTRIES.add(new Country("SO", "Somalia", "+252", 0, 1));
        COUNTRIES.add(new Country("SR", "Suriname", "+597", 0, 1));
        COUNTRIES.add(new Country("SS", "South Sudan", "+211", 0, 1));
        COUNTRIES.add(new Country("ST", "Sao Tome and Principe", "+239", 0, 1));
        COUNTRIES.add(new Country("SV", "El Salvador", "+503", 0, 1));
        COUNTRIES.add(new Country("SX", "Sint Maarten", "+1", 0, 1));
        COUNTRIES.add(new Country("SY", "Syrian Arab Republic", "+963", 0, 1));
        COUNTRIES.add(new Country("SZ", "Swaziland", "+268", 0, 1));
        COUNTRIES.add(new Country("TC", "Turks and Caicos Islands", "+1", 0, 1));
        COUNTRIES.add(new Country("TD", "Chad", "+235", 0, 1));
        COUNTRIES.add(new Country("TF", "French Southern Territories", "+262", 0, 1));
        COUNTRIES.add(new Country("TG", "Togo", "+228", 0, 1));
        COUNTRIES.add(new Country("TH", "Thailand", "+66", 0, 1));
        COUNTRIES.add(new Country("TJ", "Tajikistan", "+992", 0, 1));
        COUNTRIES.add(new Country("TK", "Tokelau", "+690", 0, 1));
        COUNTRIES.add(new Country("TL", "East Timor", "+670", 0, 1));
        COUNTRIES.add(new Country("TM", "Turkmenistan", "+993", 0, 1));
        COUNTRIES.add(new Country("TN", "Tunisia", "+216", 0, 1));
        COUNTRIES.add(new Country("TO", "Tonga", "+676", 0, 1));
        COUNTRIES.add(new Country("TR", "Turkey", "+90", 0, 1));
        COUNTRIES.add(new Country("TT", "Trinidad and Tobago", "+1", 0, 1));
        COUNTRIES.add(new Country("TV", "Tuvalu", "+688", 0, 1));
        COUNTRIES.add(new Country("TW", "Taiwan", "+886", 0, 1));
        COUNTRIES.add(new Country("TZ", "Tanzania, United Republic of", "+255", 0, 1));
        COUNTRIES.add(new Country("UA", "Ukraine", "+380", 0, 1));
        COUNTRIES.add(new Country("UG", "Uganda", "+256", 0, 1));
        COUNTRIES.add(new Country("UM", "U.S. Minor Outlying Islands", "", 0, 1));
        COUNTRIES.add(new Country("US", "United States", "+1", 0, 1));
        COUNTRIES.add(new Country("UY", "Uruguay", "+598", 0, 1));
        COUNTRIES.add(new Country("UZ", "Uzbekistan", "+998", 0, 1));
        COUNTRIES.add(new Country("VA", "Holy See (Vatican City State)", "+379", 0, 1));
        COUNTRIES.add(new Country("VC", "Saint Vincent and the Grenadines", "+1", 0, 1));
        COUNTRIES.add(new Country("VE", "Venezuela, Bolivarian Republic of", "+58", 0, 1));
        COUNTRIES.add(new Country("VG", "Virgin Islands, British", "+1", 0, 1));
        COUNTRIES.add(new Country("VI", "Virgin Islands, U.S.", "+1", 0, 1));
        COUNTRIES.add(new Country("VN", "Viet Nam", "+84", 0, 1));
        COUNTRIES.add(new Country("VU", "Vanuatu", "+678", 0, 1));
        COUNTRIES.add(new Country("WF", "Wallis and Futuna", "+681", 0, 1));
        COUNTRIES.add(new Country("WS", "Samoa", "+685", 0, 1));
        COUNTRIES.add(new Country("XK", "Kosovo", "+383", 0, 1));
        COUNTRIES.add(new Country("YE", "Yemen", "+967", 0, 1));
        COUNTRIES.add(new Country("YT", "Mayotte", "+262", 0, 1));
        COUNTRIES.add(new Country("ZA", "South Africa", "+27", 0, 1));
        COUNTRIES.add(new Country("ZM", "Zambia", "+260", 0, 1));
        COUNTRIES.add(new Country("ZW", "Zimbabwe", "+263", 0, 1));

    }

    public Country getCountry(String code) {

        for (int i = 0; i < COUNTRIES.size(); i++) {
            if (COUNTRIES.get(i).getCode().equals(code)) {
                return COUNTRIES.get(i);
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getLength_min() {
        return length_min;
    }

    public void setLength_min(int length_min) {
        this.length_min = length_min;
    }

    public int getLength_max() {
        return length_max;
    }

    public void setLength_max(int length_max) {
        this.length_max = length_max;
    }
}

