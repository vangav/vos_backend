/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 * */

/**
 * Community
 * Facebook Group: Vangav Open Source - Backend
 *   fb.com/groups/575834775932682/
 * Facebook Page: Vangav
 *   fb.com/vangav.f
 * 
 * Third party communities for Vangav Backend
 *   - play framework
 *   - cassandra
 *   - datastax
 *   
 * Tag your question online (e.g.: stack overflow, etc ...) with
 *   #vangav_backend
 *   to easier find questions/answers online
 * */

package com.vangav.backend.geo.reverse_geo_coding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CountriesAndContinents contains mappings for all country/continent code/name
 * */
public class CountriesAndContinents {

  private static CountriesAndContinents instance = null;
  
  /**
   * load
   * loads countries and continenets into memory
   */
  public static void load () {
    
    if (instance == null) {
    
      instance = new CountriesAndContinents();
    }
  }
  
  /**
   * i
   * singleton method that returns a static instance
   * @return CountriesAndContinents static instance
   * */
  public static CountriesAndContinents i () {
    
    if (instance == null) {
    
      instance = new CountriesAndContinents();
    }
    
    return instance;
  }
  
  private List<String> countryCodes;
  private List<String> countryNames;
  private List<String> continentCodes;
  private Map<String, String> continentCodeToName;
  
  private CountriesAndContinents () {
  
    this.init();
  }
  
  private void init () {
    
    String[] countryCodesArr = new String[] {
      "AF",
      "AX",
      "AL",
      "DZ",
      "AS",
      "AD",
      "AO",
      "AI",
      "AQ",
      "AG",
      "AR",
      "AM",
      "AW",
      "AU",
      "AT",
      "AZ",
      "BS",
      "BH",
      "BD",
      "BB",
      "BY",
      "BE",
      "BZ",
      "BJ",
      "BM",
      "BT",
      "BO",
      "BA",
      "BW",
      "BV",
      "BR",
      "IO",
      "VG",
      "BN",
      "BG",
      "BF",
      "BI",
      "KH",
      "CM",
      "CA",
      "CV",
      "KY",
      "CF",
      "TD",
      "CL",
      "CN",
      "CX",
      "CC",
      "CO",
      "KM",
      "CD",
      "CG",
      "CK",
      "CR",
      "CI",
      "HR",
      "CU",
      "CY",
      "CZ",
      "DK",
      "DJ",
      "DM",
      "DO",
      "EC",
      "EG",
      "SV",
      "GQ",
      "ER",
      "EE",
      "ET",
      "FO",
      "FK",
      "FJ",
      "FI",
      "FR",
      "GF",
      "PF",
      "TF",
      "GA",
      "GM",
      "GE",
      "DE",
      "GH",
      "GI",
      "GR",
      "GL",
      "GD",
      "GP",
      "GU",
      "GT",
      "GG",
      "GN",
      "GW",
      "GY",
      "HT",
      "HM",
      "VA",
      "HN",
      "HK",
      "HU",
      "IS",
      "IN",
      "ID",
      "IR",
      "IQ",
      "IE",
      "IM",
      "IL",
      "IT",
      "JM",
      "JP",
      "JE",
      "JO",
      "KZ",
      "KE",
      "KI",
      "KP",
      "KR",
      "KW",
      "KG",
      "LA",
      "LV",
      "LB",
      "LS",
      "LR",
      "LY",
      "LI",
      "LT",
      "LU",
      "MO",
      "MK",
      "MG",
      "MW",
      "MY",
      "MV",
      "ML",
      "MT",
      "MH",
      "MQ",
      "MR",
      "MU",
      "YT",
      "MX",
      "FM",
      "MD",
      "MC",
      "MN",
      "ME",
      "MS",
      "MA",
      "MZ",
      "MM",
      "NA",
      "NR",
      "NP",
      "AN",
      "NL",
      "NC",
      "NZ",
      "NI",
      "NE",
      "NG",
      "NU",
      "NF",
      "MP",
      "NO",
      "OM",
      "PK",
      "PW",
      "PS",
      "PA",
      "PG",
      "PY",
      "PE",
      "PH",
      "PN",
      "PL",
      "PT",
      "PR",
      "QA",
      "RE",
      "RO",
      "RU",
      "RW",
      "BL",
      "SH",
      "KN",
      "LC",
      "MF",
      "PM",
      "VC",
      "WS",
      "SM",
      "ST",
      "SA",
      "SN",
      "RS",
      "SC",
      "SL",
      "SG",
      "SK",
      "SI",
      "SB",
      "SO",
      "ZA",
      "GS",
      "ES",
      "LK",
      "SD",
      "SR",
      "SJ",
      "SZ",
      "SE",
      "CH",
      "SY",
      "TW",
      "TJ",
      "TZ",
      "TH",
      "TL",
      "TG",
      "TK",
      "TO",
      "TT",
      "TN",
      "TR",
      "TM",
      "TC",
      "TV",
      "UG",
      "UA",
      "AE",
      "GB",
      "US",
      "UM",
      "VI",
      "UY",
      "UZ",
      "VU",
      "VE",
      "VN",
      "WF",
      "EH",
      "YE",
      "ZM",
      "ZW"
    };
    
    this.countryCodes = Arrays.asList(countryCodesArr);
    
    String[] countryNamesArr = new String[] {
      "Afghanistan, Islamic Republic of",
      "Aland Islands",
      "Albania",
      "Algeria",
      "American Samoa",
      "Andorra",
      "Angola",
      "Anguilla",
      "Antarctica",
      "Antigua and Barbuda",
      "Argentina",
      "Armenia",
      "Aruba",
      "Australia",
      "Austria",
      "Azerbaijan",
      "Bahamas",
      "Bahrain",
      "Bangladesh",
      "Barbados",
      "Belarus",
      "Belgium",
      "Belize",
      "Benin",
      "Bermuda",
      "Bhutan",
      "Bolivia",
      "Bosnia and Herzegovina",
      "Botswana",
      "Bouvet Island (Bouvetoya)",
      "Brazil",
      "British Indian Ocean Territory (Chagos Archipelago)",
      "British Virgin Islands",
      "Brunei Darussalam",
      "Bulgaria",
      "Burkina Faso",
      "Burundi",
      "Cambodia",
      "Cameroon",
      "Canada",
      "Cape Verde",
      "Cayman Islands",
      "Central African Republic",
      "Chad",
      "Chile",
      "China",
      "Christmas Island",
      "Cocos (Keeling) Islands",
      "Colombia",
      "Comoros",
      "Congo",
      "Congo",
      "Cook Islands",
      "Costa Rica",
      "Cote d'Ivoire",
      "Croatia",
      "Cuba",
      "Cyprus",
      "Czech Republic",
      "Denmark",
      "Djibouti",
      "Dominica",
      "Dominican Republic",
      "Ecuador",
      "Egypt",
      "El Salvador",
      "Equatorial Guinea",
      "Eritrea",
      "Estonia",
      "Ethiopia",
      "Faroe Islands",
      "Falkland Islands (Malvinas)",
      "Fiji",
      "Finland",
      "France",
      "French Guiana",
      "French Polynesia",
      "French Southern Territories",
      "Gabon",
      "Gambia",
      "Georgia",
      "Germany",
      "Ghana",
      "Gibraltar",
      "Greece",
      "Greenland",
      "Grenada",
      "Guadeloupe",
      "Guam",
      "Guatemala",
      "Guernsey",
      "Guinea",
      "Guinea-Bissau",
      "Guyana",
      "Haiti",
      "Heard Island and McDonald Islands",
      "Holy See (Vatican City State)",
      "Honduras",
      "Hong Kong",
      "Hungary",
      "Iceland",
      "India",
      "Indonesia",
      "Iran",
      "Iraq",
      "Ireland",
      "Isle of Man",
      "Israel",
      "Italy",
      "Jamaica",
      "Japan",
      "Jersey",
      "Jordan",
      "Kazakhstan",
      "Kenya",
      "Kiribati",
      "Korea",
      "Korea",
      "Kuwait",
      "Kyrgyz Republic",
      "Lao People's Democratic Republic",
      "Latvia",
      "Lebanon",
      "Lesotho",
      "Liberia",
      "Libyan Arab Jamahiriya",
      "Liechtenstein",
      "Lithuania",
      "Luxembourg",
      "Macao, Special Administrative Region of China",
      "Macedonia",
      "Madagascar",
      "Malawi",
      "Malaysia",
      "Maldives",
      "Mali",
      "Malta",
      "Marshall Islands",
      "Martinique",
      "Mauritania",
      "Mauritius",
      "Mayotte",
      "Mexico",
      "Micronesia",
      "Moldova",
      "Monaco",
      "Mongolia",
      "Montenegro",
      "Montserrat",
      "Morocco",
      "Mozambique",
      "Myanmar",
      "Namibia",
      "Nauru",
      "Nepal",
      "Netherlands Antilles",
      "Netherlands",
      "New Caledonia",
      "New Zealand",
      "Nicaragua",
      "Niger",
      "Nigeria",
      "Niue",
      "Norfolk Island",
      "Northern Mariana Islands",
      "Norway",
      "Oman",
      "Pakistan",
      "Palau",
      "Palestinian Territory",
      "Panama",
      "Papua New Guinea",
      "Paraguay",
      "Peru",
      "Philippines",
      "Pitcairn Islands",
      "Poland",
      "Portugal",
      "Puerto Rico",
      "Qatar",
      "Reunion",
      "Romania",
      "Russian Federation",
      "Rwanda",
      "Saint Barthelemy",
      "Saint Helena",
      "Saint Kitts and Nevis",
      "Saint Lucia",
      "Saint Martin",
      "Saint Pierre and Miquelon",
      "Saint Vincent and the Grenadines",
      "Samoa",
      "San Marino",
      "Sao Tome and Principe",
      "Saudi Arabia",
      "Senegal",
      "Serbia",
      "Seychelles",
      "Sierra Leone",
      "Singapore",
      "Slovakia (Slovak Republic)",
      "Slovenia",
      "Solomon Islands",
      "Somalia",
      "South Africa",
      "South Georgia and the South Sandwich Islands",
      "Spain",
      "Sri Lanka",
      "Sudan",
      "Suriname",
      "Svalbard & Jan Mayen Islands",
      "Swaziland",
      "Sweden",
      "Switzerland",
      "Syrian Arab Republic",
      "Taiwan",
      "Tajikistan",
      "Tanzania",
      "Thailand",
      "Timor-Leste",
      "Togo",
      "Tokelau",
      "Tonga",
      "Trinidad and Tobago",
      "Tunisia",
      "Turkey",
      "Turkmenistan",
      "Turks and Caicos Islands",
      "Tuvalu",
      "Uganda",
      "Ukraine",
      "United Arab Emirates",
      "United Kingdom",
      "United States of America",
      "United States Minor Outlying Islands",
      "United States Virgin Islands",
      "Uruguay",
      "Uzbekistan",
      "Vanuatu",
      "Venezuela",
      "Vietnam",
      "Wallis and Futuna",
      "Western Sahara",
      "Yemen",
      "Zambia",
      "Zimbabwe"
    };
    
    this.countryNames = Arrays.asList(countryNamesArr);
    
    String[] continentCodesArr = new String[] {
      "AS",
      "EU",
      "EU",
      "AF",
      "OC",
      "EU",
      "AF",
      "NA",
      "AN",
      "NA",
      "SA",
      "AS",
      "NA",
      "OC",
      "EU",
      "AS",
      "NA",
      "AS",
      "AS",
      "NA",
      "EU",
      "EU",
      "NA",
      "AF",
      "NA",
      "AS",
      "SA",
      "EU",
      "AF",
      "AN",
      "SA",
      "AS",
      "NA",
      "AS",
      "EU",
      "AF",
      "AF",
      "AS",
      "AF",
      "NA",
      "AF",
      "NA",
      "AF",
      "AF",
      "SA",
      "AS",
      "AS",
      "AS",
      "SA",
      "AF",
      "AF",
      "AF",
      "OC",
      "NA",
      "AF",
      "EU",
      "NA",
      "AS",
      "EU",
      "EU",
      "AF",
      "NA",
      "NA",
      "SA",
      "AF",
      "NA",
      "AF",
      "AF",
      "EU",
      "AF",
      "EU",
      "SA",
      "OC",
      "EU",
      "EU",
      "SA",
      "OC",
      "AN",
      "AF",
      "AF",
      "AS",
      "EU",
      "AF",
      "EU",
      "EU",
      "NA",
      "NA",
      "NA",
      "OC",
      "NA",
      "EU",
      "AF",
      "AF",
      "SA",
      "NA",
      "AN",
      "EU",
      "NA",
      "AS",
      "EU",
      "EU",
      "AS",
      "AS",
      "AS",
      "AS",
      "EU",
      "EU",
      "AS",
      "EU",
      "NA",
      "AS",
      "EU",
      "AS",
      "AS",
      "AF",
      "OC",
      "AS",
      "AS",
      "AS",
      "AS",
      "AS",
      "EU",
      "AS",
      "AF",
      "AF",
      "AF",
      "EU",
      "EU",
      "EU",
      "AS",
      "EU",
      "AF",
      "AF",
      "AS",
      "AS",
      "AF",
      "EU",
      "OC",
      "NA",
      "AF",
      "AF",
      "AF",
      "NA",
      "OC",
      "EU",
      "EU",
      "AS",
      "EU",
      "NA",
      "AF",
      "AF",
      "AS",
      "AF",
      "OC",
      "AS",
      "NA",
      "EU",
      "OC",
      "OC",
      "NA",
      "AF",
      "AF",
      "OC",
      "OC",
      "OC",
      "EU",
      "AS",
      "AS",
      "OC",
      "AS",
      "NA",
      "OC",
      "SA",
      "SA",
      "AS",
      "OC",
      "EU",
      "EU",
      "NA",
      "AS",
      "AF",
      "EU",
      "EU",
      "AF",
      "NA",
      "AF",
      "NA",
      "NA",
      "NA",
      "NA",
      "NA",
      "OC",
      "EU",
      "AF",
      "AS",
      "AF",
      "EU",
      "AF",
      "AF",
      "AS",
      "EU",
      "EU",
      "OC",
      "AF",
      "AF",
      "AN",
      "EU",
      "AS",
      "AF",
      "SA",
      "EU",
      "AF",
      "EU",
      "EU",
      "AS",
      "AS",
      "AS",
      "AF",
      "AS",
      "AS",
      "AF",
      "OC",
      "OC",
      "NA",
      "AF",
      "AS",
      "AS",
      "NA",
      "OC",
      "AF",
      "EU",
      "AS",
      "EU",
      "NA",
      "OC",
      "NA",
      "SA",
      "AS",
      "OC",
      "SA",
      "AS",
      "OC",
      "AF",
      "AS",
      "AF",
      "AF"
    };
    
    this.continentCodes = Arrays.asList(continentCodesArr);
    
    this.continentCodeToName = new HashMap<String, String>();
    
    this.continentCodeToName.put("NA", "North America");
    this.continentCodeToName.put("SA", "South America");
    this.continentCodeToName.put("AN", "Antarctica");
    this.continentCodeToName.put("AF", "Africa");
    this.continentCodeToName.put("EU", "Europe");
    this.continentCodeToName.put("AS", "Asia");
    this.continentCodeToName.put("OC", "Oceania");
  }
  
  private static final String kUnknown = "UN_KNOWN";
  
  /**
   * validValue checks if a country's or continenet's name/code is valid
   * @param value: country/continent name
   * @return boolean (true for valid, false for invalid)
   */
  public boolean validValue (String value) {
    
    if (value.compareTo(kUnknown) == 0) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getCountryName
   * @param countryCode
   * @return String country name
   * @throws Exception
   */
  public String getCountryName (String countryCode) throws Exception {
    
    if (this.countryCodes.contains(countryCode) == true) {
      
      return
        this.countryNames.get(
          this.countryCodes.indexOf(countryCode) ).toString();
    }
    
    return kUnknown;
  }
  
  /**
   * getContinentCode
   * @param countryCode
   * @return String continent code
   * @throws Exception
   */
  public String getContinentCode (String countryCode) throws Exception {
    
    if (this.countryCodes.contains(countryCode) == true) {
      
      return
        this.continentCodes.get(
          this.countryCodes.indexOf(countryCode) ).toString();
    }
    
    return kUnknown;
  }
  
  /**
   * getContinentName
   * @param continentCode
   * @return String continent name
   * @throws Exception
   */
  public String getContinentName (String continentCode) throws Exception {
    
    if (this.continentCodeToName.containsKey(continentCode) == true) {
      
      return this.continentCodeToName.get(continentCode);
    }
    
    return kUnknown;
  }
}
