package technology.infobite.com.sportsforsports.constant;

public class Constant {
    /*http://codeencrypt.in/sport/admin/api/league_list.php*/
    public static final String BASE_URL = "http://codeencrypt.in/sport/admin/api/";
    public static final String LOGIN_API = "user-login.php";
    public static final String REGISTRATION_API = "user_signup.php";
    public static final String UPDATE_PROFILE_API = "update_profile.php";
    public static final String USER_PROFILE_API = "user_profile.php";
    public static final String NEWPOST_API = "post_feed.php";
    public static final String TIMELINE_API = "timeline.php";
    public static final String POST_COMMENT_API = "post_comment.php";
    public static final String PostLikeAPI = "post_like.php";
    public static final String USER_LIST = "athlete.php";
    public static final String FOLLOW_API = "fan.php";
    public static final String CHECK_FOLLOW = "check_following.php";
    public static final String NOTIFICATION_LIST = "notification.php";
    public static final String UPDATE_TOKEN = "token.php";
    public static final String SINGLE_POST = "single_post.php";
    public static final String DELETE_POST = "delete_post.php";
    public static final String UPDATE_PROFILE_IMAGE = "update_profile_image.php";
    public static final String LEAGUE_LIST = "select_league.php";
    public static final String LEAGUE_FOLLOWING_LIST = "league_list.php";
    public static final String LEAGUE_FOLLOW = "league_follow.php";

    public static final String EMAIL_OTP = "user-resend-otp.php";
    public static final String VERIFY_OTP = "otp_match.php";
    public static final String NEW_PASSWORD = "change_password.php";

    /* Fragment tag */
    public static final String TimelineFragment = "TimelineFragment";
    public static final String ProfileFragment = "ProfileFragment";
    public static final String NotificationFragment = "NotificationFragment";
    public static final String SettingFragment = "SettingFragment";
    public static final String VideoGalleryFragment = "VideoGalleryFragment";
    public static final String VideoTrimmerFragment = "VideoTrimmerFragment";

    /* Preference */
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_IMAGE = "USER_IMAGE";
    public static final String POST_DETAIL = "POST_DETAIL";
    public static final String TIMELINE_DATA = "TIMELINE_DATA";
    public static final String USER_TIMELINE_DATA = "USER_TIMELINE_DATA";
    public static final String IS_DATA_UPDATE = "IS_DATA_UPDATE";
    public static final String TOKEN = "TOKEN";

    /************************************************************************/
    public static final String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String[] countries = new String[]{"Select country", "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola",
            "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria",
            "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin",
            "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil",
            "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia",
            "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
            "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the",
            "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic",
            "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador",
            "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji",
            "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories",
            "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada",
            "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
    public static final String[] heightUnit = new String[]{"Meter", "Inches"};
    public static final String[] weightUnit = new String[]{"Kg", "Pounds"};

    public static final String DEMO_URL = "https://mail-attachment.googleusercontent.com/attachment/u/0/?ui=2&ik=95feff9366&attid=0.1&permmsgid=msg-f:1621985822895568602&th=168273832468f6da&view=att&disp=inline&realattid=f_jqm0aavt0&saddbat=ANGjdJ9UC0MXfarPlwbD-Io9fL4D8eEKiWZsX-sDtRfg-rg8HU5kBm07vLciLg_aKeuiGWSnTq_dD-Vhpd7-SRKz3zUO9NKwnAlc-DnpLM_6MI2gBKhBvCxLeRWNvq1AlW_OOYdkx543UrZ79cmjob_hq32eXqIZo57__2Mtlx7qSaQbFhzLJBYCvEEM9_RaPiYAUt0x5FJveljJ8DyGFIFVIkx1W6ihjm_sD1oPBobRhMYYd7i_a9CWU6QzG-4tlNyTvVsEOIUIA2pQVbn3-TJPivFIcJnM0KxmT2rMs68bMGQy3Ulg49kSzGG_KaqbtUcC-1DJ0W6IKfQlhl_5fHSZtuqWEIwYJeb6f69gnK3gFRLvaJLpJXJ4_mp_IMzkZaz5pN8gqU3gFq1nfmTItpJX2ilSi4-5VpQKPs8GHrIiYRvtA4xNQY8lfTH_iVn4WENVmkZ-bVGuu8lvdEavzn3KDhC6qb_B5QB46mYGDh37snNTxB68ZaX1XKLXf8dAhTjfwVbuPUDuJ9-fEjRLqq-LE7nwBHZ3ZPnNoPcEe0geexo19zo4SyOHzJRFHDc5TYjY97e14_gPH_6PW8xgut2kMY96f_PUkd2jeuxAUinZFnBGudhTvTKL10VayNRdbVQ3OqpBnro8IrpnXvBO";
    public static final String IMAGE_BASE_URL = "http://codeencrypt.in/sport/images/alhlete_images/";
    public static final String VIDEO_URL = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    public static final String VIDEO_BASE_URL = "http://codeencrypt.in/sport/images/alhlete_video/";
    public static final String PROFILE_IMAGE_BASE_URL = "http://codeencrypt.in/sport/images/profile_img/";
    public static final String LEAGUE_IMAGE_BASE_URL = "http://codeencrypt.in/sport/images/league/";
    public static final String DEFAULT_IMAGE_URL = "https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png";
}

/**/