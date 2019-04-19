package com.pinlinx.constant;

public class Constant {

    /*public static final String ImageVideoBaseUrl = "http://codeencrypt.in/sport/";
    public static final String BASE_URL = "http://codeencrypt.in/sport/admin/api/";*/

    public static final String ImageVideoBaseUrl = "http://pinlinx.com/";
    public static final String BASE_URL = "http://pinlinx.com/admin/api/";

    public static final String FB_API = "user_social_login.php";
    public static final String LOGIN_API = "user-login.php";
    public static final String REGISTRATION_API = "user_signup.php";
    public static final String UPDATE_PROFILE_API = "update_profile.php";
    public static final String USER_PROFILE_API = "user_profile.php";
    public static final String NEWPOST_API = "post_feed.php";
    public static final String TIMELINE_API = "timeline.php";
    public static final String POST_COMMENT_API = "post_comment.php";
    public static final String PostLikeAPI = "post_like.php";
    public static final String USER_LIST = "athlete.php";
    public static final String MY_FANS = "my_fans.php";
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
    public static final String[] Discipline = new String[]{"Professional", "Amateur", "Beginner"};
    public static final String[] Level = new String[]{"Club", "College", "School", "Company"};

    public static final String DEMO_URL = "https://mail-attachment.googleusercontent.com/attachment/u/0/?ui=2&ik=95feff9366&attid=0.1&permmsgid=msg-f:1621985822895568602&th=168273832468f6da&view=att&disp=inline&realattid=f_jqm0aavt0&saddbat=ANGjdJ9UC0MXfarPlwbD-Io9fL4D8eEKiWZsX-sDtRfg-rg8HU5kBm07vLciLg_aKeuiGWSnTq_dD-Vhpd7-SRKz3zUO9NKwnAlc-DnpLM_6MI2gBKhBvCxLeRWNvq1AlW_OOYdkx543UrZ79cmjob_hq32eXqIZo57__2Mtlx7qSaQbFhzLJBYCvEEM9_RaPiYAUt0x5FJveljJ8DyGFIFVIkx1W6ihjm_sD1oPBobRhMYYd7i_a9CWU6QzG-4tlNyTvVsEOIUIA2pQVbn3-TJPivFIcJnM0KxmT2rMs68bMGQy3Ulg49kSzGG_KaqbtUcC-1DJ0W6IKfQlhl_5fHSZtuqWEIwYJeb6f69gnK3gFRLvaJLpJXJ4_mp_IMzkZaz5pN8gqU3gFq1nfmTItpJX2ilSi4-5VpQKPs8GHrIiYRvtA4xNQY8lfTH_iVn4WENVmkZ-bVGuu8lvdEavzn3KDhC6qb_B5QB46mYGDh37snNTxB68ZaX1XKLXf8dAhTjfwVbuPUDuJ9-fEjRLqq-LE7nwBHZ3ZPnNoPcEe0geexo19zo4SyOHzJRFHDc5TYjY97e14_gPH_6PW8xgut2kMY96f_PUkd2jeuxAUinZFnBGudhTvTKL10VayNRdbVQ3OqpBnro8IrpnXvBO";
    public static final String IMAGE_BASE_URL = ImageVideoBaseUrl + "images/alhlete_images/";
    public static final String VIDEO_BASE_URL = ImageVideoBaseUrl + "images/alhlete_video/";
    public static final String PROFILE_IMAGE_BASE_URL = ImageVideoBaseUrl + "images/profile_img/";
    public static final String LEAGUE_IMAGE_BASE_URL = ImageVideoBaseUrl + "images/league/";
    public static final String DEFAULT_IMAGE_URL = "https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png";

    public static final String TERMS = "<p><strong>Terms and conditions</strong></p>\n" +
            "<p>These terms and conditions (\"Terms\", \"Agreement\") are an agreement between Mobile Application Developer (\"Mobile Application Developer\", \"us\", \"we\" or \"our\") and you (\"User\", \"you\" or \"your\"). This Agreement sets forth the general terms and conditions of your use of the Pinlinx mobile application and any of its products or services (collectively, \"Mobile Application\" or \"Services\").</p>\n" +
            "<p><strong>Accounts and membership</strong></p>\n" +
            "<p>You must be at least 13 years of age to use this Mobile Application. By using this Mobile Application and by agreeing to this Agreement you warrant and represent that you are at least 13 years of age. If you create an account in the Mobile Application, you are responsible for maintaining the security of your account and you are fully responsible for all activities that occur under the account and any other actions taken in connection with it. Providing false contact information of any kind may result in the termination of your account. You must immediately notify us of any unauthorized uses of your account or any other breaches of security. We will not be liable for any acts or omissions by you, including any damages of any kind incurred as a result of such acts or omissions. We may suspend, disable, or delete your account (or any part thereof) if we determine that you have violated any provision of this Agreement or that your conduct or content would tend to damage our reputation and goodwill. If we delete your account for the foregoing reasons, you may not re-register for our Services. We may block your email address and Internet protocol address to prevent further registration.</p>\n" +
            "<p><strong>User content</strong></p>\n" +
            "<p>We do not own any data, information or material (\"Content\") that you submit in the Mobile Application in the course of using the Service. You shall have sole responsibility for the accuracy, quality, integrity, legality, reliability, appropriateness, and intellectual property ownership or right to use of all submitted Content. We may, but have no obligation to, monitor Content in the Mobile Application submitted or created using our Services by you. Unless specifically permitted by you, your use of the Mobile Application does not grant us the license to use, reproduce, adapt, modify, publish or distribute the Content created by you or stored in your user account for commercial, marketing or any similar purpose. But you grant us permission to access, copy, distribute, store, transmit, reformat, display and perform the Content of your user account solely as required for the purpose of providing the Services to you. Without limiting any of those representations or warranties, we have the right, though not the obligation, to, in our own sole discretion, refuse or remove any Content that, in our reasonable opinion, violates any of our policies or is in any way harmful or objectionable.</p>\n" +
            "<p><strong>Backups</strong></p>\n" +
            "<p>We perform regular backups of the Content and will do our best to ensure completeness and accuracy of these backups. In the event of the hardware failure or data loss we will restore backups automatically to minimize the impact and downtime.</p>\n" +
            "<p><strong>Advertisements</strong></p>\n" +
            "<p>During use of the Mobile Application, you may enter into correspondence with or participate in promotions of advertisers or sponsors showing their goods or services through the Mobile Application. Any such activity, and any terms, conditions, warranties or representations associated with such activity, is solely between you and the applicable third-party. We shall have no liability, obligation or responsibility for any such correspondence, purchase or promotion between you and any such third-party.</p>\n" +
            "<p><strong>Prohibited uses</strong></p>\n" +
            "<p>In addition to other terms as set forth in the Agreement, you are prohibited from using the Mobile Application or its Content: (a) for any unlawful purpose; (b) to solicit others to perform or participate in any unlawful acts; (c) to violate any international, federal, provincial or state regulations, rules, laws, or local ordinances; (d) to infringe upon or violate our intellectual property rights or the intellectual property rights of others; (e) to harass, abuse, insult, harm, defame, slander, disparage, intimidate, or discriminate based on gender, sexual orientation, religion, ethnicity, race, age, national origin, or disability; (f) to submit false or misleading information; (g) to upload or transmit viruses or any other type of malicious code that will or may be used in any way that will affect the functionality or operation of the Service or of any related mobile application, other mobile applications, or the Internet; (h) to collect or track the personal information of others; (i) to spam, phish, pharm, pretext, spider, crawl, or scrape; (j) for any obscene or immoral purpose; or (k) to interfere with or circumvent the security features of the Service or any related mobile application, other mobile applications, or the Internet. We reserve the right to terminate your use of the Service or any related mobile application for violating any of the prohibited uses.</p>\n" +
            "<p><strong>Intellectual property rights</strong></p>\n" +
            "<p>This Agreement does not transfer to you any intellectual property owned by Mobile Application Developer or third-parties, and all rights, titles, and interests in and to such property will remain (as between the parties) solely with Mobile Application Developer. All trademarks, service marks, graphics and logos used in connection with our Mobile Application or Services, are trademarks or registered trademarks of Mobile Application Developer or Mobile Application Developer licensors. Other trademarks, service marks, graphics and logos used in connection with our Mobile Application or Services may be the trademarks of other third-parties. Your use of our Mobile Application and Services grants you no right or license to reproduce or otherwise use any Mobile Application Developer or third-party trademarks.</p>\n" +
            "<p><strong>Changes and amendments</strong></p>\n" +
            "<p>We reserve the right to modify this Agreement or its policies relating to the Mobile Application or Services at any time, effective upon posting of an updated version of this Agreement in the Mobile Application. When we do, we will revise the updated date at the bottom of this page. Continued use of the Mobile Application after any such changes shall constitute your consent to such changes.</p>\n" +
            "<p>&nbsp;</p>\n" +
            "<p><strong>Acceptance of these terms</strong></p>\n" +
            "<p>You acknowledge that you have read this Agreement and agree to all its terms and conditions. By using the Mobile Application or its Services you agree to be bound by this Agreement. If you do not agree to abide by the terms of this Agreement, you are not authorized to use or access the Mobile Application and its Services.</p>\n" +
            "<p><strong>Contacting us</strong></p>\n" +
            "<p>If you have any questions about this Agreement, please contact us.</p>\n" +
            "<p>This document was last updated on April 16, 2019</p>";

    public static final String PRIVACY_POLICY = "<p><strong>Privacy policy</strong></p>\n" +
            "<p>This privacy policy (\"Policy\") describes how Mobile Application Developer (\"Mobile Application Developer\", \"we\", \"us\" or \"our\") collects, protects and uses the personally identifiable information (\"Personal Information\") you (\"User\", \"you\" or \"your\") may provide in the Pinlinx mobile application and any of its products or services (collectively, \"Mobile Application\" or \"Services\"). It also describes the choices available to you regarding our use of your Personal Information and how you can access and update this information. This Policy does not apply to the practices of companies that we do not own or control, or to individuals that we do not employ or manage.</p>\n" +
            "<p><strong>Collection of personal information</strong></p>\n" +
            "<p>We receive and store any information you knowingly provide to us when you create an account, publish content, fill any online forms in the Mobile Application. When required this information may include your email address, name, phone number, address, or other Personal Information. You can choose not to provide us with certain information, but then you may not be able to take advantage of some of the Mobile Application's features. Users who are uncertain about what information is mandatory are welcome to contact us.</p>\n" +
            "<p><strong>Collection of non-personal information</strong></p>\n" +
            "<p>When you open the Mobile Application our servers automatically record information that your device sends. This data may include information such as your device's IP address and location, device name and version, operating system type and version, language preferences, information you search for in our Mobile Application, access times and dates, and other statistics.</p>\n" +
            "<p><strong>Managing personal information</strong></p>\n" +
            "<p>You are able to access, add to, update and delete certain Personal Information about you. The information you can view, update, and delete may change as the Mobile Application or Services change. When you update information, however, we may maintain a copy of the unrevised information in our records. Some information may remain in our private records after your deletion of such information from your account. We will retain and use your information as necessary to comply with our legal obligations, resolve disputes, and enforce our agreements. We may use any aggregated data derived from or incorporating your Personal Information after you update or delete it, but not in a manner that would identify you personally. Once the retention period expires, Personal Information shall be deleted. Therefore, the right to access, the right to erasure, the right to rectification and the right to data portability cannot be enforced after the expiration of the retention period.</p>\n" +
            "<p><strong>Use and processing of collected information</strong></p>\n" +
            "<p>Any of the information we collect from you may be used to personalize your experience; improve our Mobile Application; improve customer service and respond to queries and emails of our customers; run and operate our Mobile Application and Services. Non-Personal Information collected is used only to identify potential cases of abuse and establish statistical information regarding Mobile Application traffic and usage. This statistical information is not otherwise aggregated in such a way that would identify any particular user of the system.</p>\n" +
            "<p>We may process Personal Information related to you if one of the following applies: (i) You have given your consent for one or more specific purposes. Note that under some legislations we may be allowed to process information until you object to such processing (by opting out), without having to rely on consent or any other of the following legal bases below. This, however, does not apply, whenever the processing of Personal Information is subject to European data protection law; (ii) Provision of information is necessary for the performance of an agreement with you and/or for any pre-contractual obligations thereof; (ii) Processing is necessary for compliance with a legal obligation to which you are subject; (iv) Processing is related to a task that is carried out in the public interest or in the exercise of official authority vested in us; (v) Processing is necessary for the purposes of the legitimate interests pursued by us or by a third party. In any case, we will be happy to clarify the specific legal basis that applies to the processing, and in particular whether the provision of Personal Data is a statutory or contractual requirement, or a requirement necessary to enter into a contract.</p>\n" +
            "<p><strong>Information transfer and storage</strong></p>\n" +
            "<p>Depending on your location, data transfers may involve transferring and storing your information in a country other than your own. You are entitled to learn about the legal basis of information transfers to a country outside the European Union or to any international organization governed by public international law or set up by two or more countries, such as the UN, and about the security measures taken by us to safeguard your information. If any such transfer takes place, you can find out more by checking the relevant sections of this document or inquire with us using the information provided in the contact section.</p>\n" +
            "<p><strong>The rights of users</strong></p>\n" +
            "<p>You may exercise certain rights regarding your information processed by us. In particular, you have the right to do the following: (i) you have the right to withdraw consent where you have previously given your consent to the processing of your information; (ii) you have the right to object to the processing of your information if the processing is carried out on a legal basis other than consent; (iii) you have the right to learn if information is being processed by us, obtain disclosure regarding certain aspects of the processing and obtain a copy of the information undergoing processing; (iv) you have the right to verify the accuracy of your information and ask for it to be updated or corrected; (v) you have the right, under certain circumstances, to restrict the processing of your information, in which case, we will not process your information for any purpose other than storing it; (vi) you have the right, under certain circumstances, to obtain the erasure of your Personal Information from us; (vii) you have the right to receive your information in a structured, commonly used and machine readable format and, if technically feasible, to have it transmitted to another controller without any hindrance. This provision is applicable provided that your information is processed by automated means and that the processing is based on your consent, on a contract which you are part of or on pre-contractual obligations thereof.</p>\n" +
            "<p><strong>The right to object to processing</strong></p>\n" +
            "<p>Where Personal Information is processed for the public interest, in the exercise of an official authority vested in us or for the purposes of the legitimate interests pursued by us, you may object to such processing by providing a ground related to your particular situation to justify the objection. You must know that, however, should your Personal Information be processed for direct marketing purposes, you can object to that processing at any time without providing any justification. To learn, whether we are processing Personal Information for direct marketing purposes, you may refer to the relevant sections of this document.</p>\n" +
            "<p><strong>How to exercise these rights</strong></p>\n" +
            "<p>Any requests to exercise User rights can be directed to the Owner through the contact details provided in this document. These requests can be exercised free of charge and will be addressed by the Owner as early as possible and always within one month.</p>\n" +
            "<p><strong>Privacy of children</strong></p>\n" +
            "<p>We do not knowingly collect any Personal Information from children under the age of 13. If you are under the age of 13, please do not submit any Personal Information through our Mobile Application or Service. We encourage parents and legal guardians to monitor their children's Internet usage and to help enforce this Policy by instructing their children never to provide Personal Information through our Mobile Application or Service without their permission. If you have reason to believe that a child under the age of 13 has provided Personal Information to us through our Mobile Application or Service, please contact us. You must also be at least 16 years of age to consent to the processing of your personal data in your country (in some countries we may allow your parent or guardian to do so on your behalf).</p>\n" +
            "<p><strong>Advertisement</strong></p>\n" +
            "<p>We may display online advertisements and we may share aggregated and non-identifying information about our customers that we collect through the registration process or through online surveys and promotions with certain advertisers. We do not share personally identifiable information about individual customers with advertisers. In some instances, we may use this aggregated and non-identifying information to deliver tailored advertisements to the intended audience.</p>\n" +
            "<p><strong>Information security</strong></p>\n" +
            "<p>We secure information you provide on computer servers in a controlled, secure environment, protected from unauthorized access, use, or disclosure. We maintain reasonable administrative, technical, and physical safeguards in an effort to protect against unauthorized access, use, modification, and disclosure of Personal Information in its control and custody. However, no data transmission over the Internet or wireless network can be guaranteed. Therefore, while we strive to protect your Personal Information, you acknowledge that (i) there are security and privacy limitations of the Internet which are beyond our control; (ii) the security, integrity, and privacy of any and all information and data exchanged between you and our Mobile Application cannot be guaranteed; and (iii) any such information and data may be viewed or tampered with in transit by a third-party, despite best efforts.</p>\n" +
            "<p><strong>Data breach</strong></p>\n" +
            "<p>In the event we become aware that the security of the Mobile Application has been compromised or users Personal Information has been disclosed to unrelated third parties as a result of external activity, including, but not limited to, security attacks or fraud, we reserve the right to take reasonably appropriate measures, including, but not limited to, investigation and reporting, as well as notification to and cooperation with law enforcement authorities. In the event of a data breach, we will make reasonable efforts to notify affected individuals if we believe that there is a reasonable risk of harm to the user as a result of the breach or if notice is otherwise required by law. When we do, we will post a notice in the Mobile Application, send you an email.</p>\n" +
            "<p><strong>Legal disclosure</strong></p>\n" +
            "<p>We will disclose any information we collect, use or receive if required or permitted by law, such as to comply with a subpoena, or similar legal process, and when we believe in good faith that disclosure is necessary to protect our rights, protect your safety or the safety of others, investigate fraud, or respond to a government request.</p>\n" +
            "<p><strong>Changes and amendments</strong></p>\n" +
            "<p>We reserve the right to modify this Policy relating to the Mobile Application or Services at any time, effective upon posting of an updated version of this Policy in the Mobile Application. When we do we will revise the updated date at the bottom of this page. Continued use of the Mobile Application after any such changes shall constitute your consent to such changes.</p>\n" +
            "<p>&nbsp;</p>\n" +
            "<p><strong>Acceptance of this policy</strong></p>\n" +
            "<p>You acknowledge that you have read this Policy and agree to all its terms and conditions. By using the Mobile Application or its Services you agree to be bound by this Policy. If you do not agree to abide by the terms of this Policy, you are not authorized to use or access the Mobile Application and its Services.</p>\n" +
            "<p><strong>Contacting us</strong></p>\n" +
            "<p>support@pinlinx.com</p>";
}

/**/