package com.constants;

public interface ContextualConstantsIF {

	public static final String MODEL_NAME = "obj";

	interface SQLS {

		/* For Reviews */
		public static final String RETRIVE_REVIEWS_SQL = "RETRIVE_REVIEWS_SQL";
		public static final String INSERT_ARTICLE_SQL = "INSERT_ARTICLE_SQL";

		/* For Stopwords */
		public static final String INSERT_STOPWORD_SQL = "INSERT_STOPWORD_SQL";
		public static final String RETRIVE_STOPWORDS_FULL_SQL = "RETRIVE_STOPWORDS_FULL_SQL";
		public static final String RETRIVE_STOPWORDS_SQL = "RETRIVE_STOPWORDS_SQL";
		public static final String REMOVE_STOPWORD_SQL = "REMOVE_STOPWORD_SQL";

		/* Tokenization */
		public static final String RETRIVE_ALLTOKENS_SQL = "RETRIVE_ALLTOKENS_SQL";
		public static final String DELETE_ALLTOKENS_SQL = "DELETE_ALLTOKENS_SQL";
		public static final String INSERT_TOKENS_SQL = "INSERT_TOKENS_SQL";

		/* Clean Reviews */
		public static final String RETRIVE_ALLCLEAN_ARTICLES_SQL = "RETRIVE_ALLCLEAN_ARTICLES_SQL";
		public static final String DELETE_ALL_CLEAN_ARTICLES_SQL = "DELETE_ALL_CLEAN_ARTICLES_SQL";
		public static final String INSERT_CLEANDETAILS_SQL = "INSERT_CLEANDETAILS_SQL";

		/* Key Phrase */
		public static final String INSERT_KEYPHRASE_SQL = "INSERT_KEYPHRASE_SQL";
		public static final String RETRIVE_KEYPHRASE_WHERE_TYPE_SQL = "RETRIVE_KEYPHRASE_WHERE_TYPE_SQL";
		public static final String RETRIVE_KEYPHRASE_FULL_SQL = "RETRIVE_KEYPHRASE_FULL_SQL";
		public static final String REMOVE_KEYPHRASE_WHERE_KEYPHRASE_AND_TYPE_SQL = "REMOVE_KEYPHRASE_WHERE_KEYPHRASE_AND_TYPE_SQL";

		/* Phrase G */

		public static final String INSERT_PHRASEG_SQL = "INSERT_PHRASEG_SQL";
		public static final String RETRIVE_PHRASEG_WHERE_TYPE_NOTYPE_SQL = "RETRIVE_PHRASEG_WHERE_TYPE_NOTYPE_SQL";
		public static final String RETRIVE_PHRASEG_FULL_SQL = "RETRIVE_PHRASEG_FULL_SQL";
		public static final String REMOVE_PHRASEG_WHERE_PHRASEG_AND_TYPE_AND_NOTYPE_SQL = "REMOVE_PHRASEG_WHERE_PHRASEG_AND_TYPE_AND_NOTYPE_SQL";

		/* Phrase */

		public static final String DELETE_ALL_PHRASES_SQL = "DELETE_ALL_PHRASES_SQL";
		public static final String RETRIVE_ONLY_ALLTOKENS_SQL = "RETRIVE_ONLY_ALLTOKENS_SQL";
		public static final String RETRIVE_ONLY_ALLKEYPHRASES_SQL = "RETRIVE_ONLY_ALLKEYPHRASES_SQL";
		public static final String RETRIVE_ONLY_ALL_ADJECTIVES_SQL = "RETRIVE_ONLY_ALL_ADJECTIVES_SQL";
		public static final String INSERT_PHRASE_SQL = "INSERT_PHRASE_SQL";
		public static final String RETRIVE_ALL_PHRASES_SQL = "RETRIVE_ALL_PHRASES_SQL";

		/* Adjectives */
		public static final String DELETE_ALL_ADJECTIVES_SQL = "DELETE_ALL_ADJECTIVES_SQL";
		public static final String INSERT_ADJECTIVE_SQL = "INSERT_ADJECTIVE_SQL";
		public static final String RETRIVE_ADJECTIVES_FULL_SQL = "RETRIVE_ADJECTIVES_FULL_SQL";

		/* Structure1 */
		public static final String INSERT_STRUCTURE1_SQL = "INSERT_STRUCTURE1_SQL";
		public static final String DELETE_ALL_STRUCTURE1_SQL = "DELETE_ALL_STRUCTURE1_SQL";
		public static final String RETRIVE_STRUCTURE1_FULL_SQL = "RETRIVE_STRUCTURE1_FULL_SQL";
		public static final String RETRIVE_STRUCTURE2_FULL_SQL = "RETRIVE_STRUCTURE2_FULL_SQL";
		public static final String RETRIVE_STRUCTURE3_FULL_SQL = "RETRIVE_STRUCTURE3_FULL_SQL";
		public static final String DELETE_ALL_STRUCTURE2_SQL = "DELETE_ALL_STRUCTURE2_SQL";
		public static final String INSERT_STRUCTURE2_SQL = "INSERT_STRUCTURE2_SQL";
		public static final String DELETE_ALL_STRUCTURE3_SQL = "DELETE_ALL_STRUCTURE3_SQL";

		/* Frequency */
		public static final String RETRIVE_ALL_PHRASES_ONLY_SQL = "RETRIVE_ALL_PHRASES_ONLY_SQL";
		public static final String INSERT_STRUCTURE3_SQL = "INSERT_STRUCTURE3_SQL";
		public static final String DELETE_ALL_FREQ_SQL = "DELETE_ALL_FREQ_SQL";
		public static final String RETRIVE_TYPES_ONLY_SQL = "RETRIVE_TYPES_ONLY_SQL";
		public static final String RETRIVE_ALL_STRUCTURE1_ONLY_SQL = "RETRIVE_ALL_STRUCTURE1_ONLY_SQL";
		public static final String RETRIVE_ALL_STRUCTURE2_ONLY_SQL = "RETRIVE_ALL_STRUCTURE2_ONLY_SQL";
		public static final String RETRIVE_ALL_STRUCTURE3_ONLY_SQL = "RETRIVE_ALL_STRUCTURE3_ONLY_SQL";
		public static final String RETRIVE_ALL_PHARSEG_ONLY_WHERE_TYPE_SQL = "RETRIVE_ALL_PHARSEG_ONLY_WHERE_TYPE_SQL";
		public static final String INSERT_FREQCOMPUTATION_SQL = "INSERT_FREQCOMPUTATION_SQL";
		public static final String RETRIVE_FREQCOMPUTATION_SQL = "RETRIVE_FREQCOMPUTATION_SQL";
		public static final String RETRIVE_DINSTINCT_TOKENS_FOR_ARTICLENAME_SQL = "RETRIVE_DINSTINCT_TOKENS_FOR_ARTICLENAME_SQL";
		public static final String RETRIVE_DINSTINCT_KEYPHRASES_FOR_TOKENS_AND_TYPE_SQL = "RETRIVE_DINSTINCT_KEYPHRASES_FOR_TOKENS_AND_TYPE_SQL";
		public static final String SELECT_COUNT_FOR_TOKENNAME_ARTICLENAME_SQL = "SELECT_COUNT_FOR_TOKENNAME_ARTICLENAME_SQL";
		public static final String SELECT_COUNT_FOR_ARTICLENAME_SQL = "SELECT_COUNT_FOR_ARTICLENAME_SQL";
		public static final String RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_SQL = "RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_SQL";
		public static final String INSERT_ADJECTIVES_NLP_SQL = "INSERT_ADJECTIVES_NLP_SQL";
		public static final String RETRIVE_ADJECTIVESNLP_SQL = "RETRIVE_ADJECTIVESNLP_SQL";
		public static final String RETRIVE_ADJECTIVESNLP_FULL_SQL = "RETRIVE_ADJECTIVESNLP_FULL_SQL";
		public static final String RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_AND_TYPE_SQL = "RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_AND_TYPE_SQL";
		public static final String RETRIVE_UNIQUE_ARTICLENAMES = "RETRIVE_UNIQUE_ARTICLENAMES";
		public static final String RETRIVE_DISTINCTTOKENS_WHERE_ARTICLENAME_SQL = "RETRIVE_DISTINCTTOKENS_WHERE_ARTICLENAME_SQL";
		public static final String RETRIVE_COUNT_WHERE_TOKENNAME_AND_ARTICLENAME_SQL = "RETRIVE_COUNT_WHERE_TOKENNAME_AND_ARTICLENAME_SQL";
		public static final String INSERT_FREQUENCY_SQL = "INSERT_FREQUENCY_SQL";
		public static final String RETRIVE_UNIQUE_ARTICLENAMES_SQL = "RETRIVE_UNIQUE_ARTICLENAMES_SQL";
		public static final String RETRIVE_UNIQUE_TOKENS_FROM_FREQ_WHERE_ARTICLENAME_SQL = "RETRIVE_UNIQUE_TOKENS_FROM_FREQ_WHERE_ARTICLENAME_SQL";
		public static final String RETRIVE_UNIQUE_FREQINFO_FROM_FREQ_WHERE_ARTICLENAME_SQL = "RETRIVE_UNIQUE_FREQINFO_FROM_FREQ_WHERE_ARTICLENAME_SQL";
		public static final String RETRIVE_COUNTOTHERS_WHERE_TOKENNAME_AND_ARTICLENAME_SQL = "RETRIVE_COUNTOTHERS_WHERE_TOKENNAME_AND_ARTICLENAME_SQL";
		public static final String RETRIVE_COUNTALL_WHERE_ARTICLENAME_SQL = "RETRIVE_COUNTALL_WHERE_ARTICLENAME_SQL";
		public static final String RETRIVE_FREQ_WHERE_ARTICLENAME_SQL = "RETRIVE_FREQ_WHERE_ARTICLENAME_SQL";
		public static final String INSERT_LOGLIKELIHOOD_SQL = "INSERT_LOGLIKELIHOOD_SQL";
		public static final String RETRIVE_LOGLIKELIHOOD_FULL_SQL = "RETRIVE_LOGLIKELIHOOD_FULL_SQL";
		public static final String DELETE_LOGLIKELIHOOD_SQL = "DELETE_LOGLIKELIHOOD_SQL";
		public static final String RETRIVE_DINSTINCT_TOKENS_FOR_ARTICLENAME_FROM_LOGLIKELIHOOD_SQL = "RETRIVE_DINSTINCT_TOKENS_FOR_ARTICLENAME_FROM_LOGLIKELIHOOD_SQL";
		public static final String RETRIVE_FREQ_FROM_LOGLIKELIHOOD_WHERE_ARTICLENAME_SQL = "RETRIVE_FREQ_FROM_LOGLIKELIHOOD_WHERE_ARTICLENAME_SQL";
		public static final String INSERT_COMPAREARTICLE_SQL = "INSERT_COMPAREARTICLE_SQL";
		public static final String RETRIVE_FREQ_FROM_LOGLIKELIHOOD_WHERE_ARTICLENAME_AND_KEYPHRASE_SQL = "RETRIVE_FREQ_FROM_LOGLIKELIHOOD_WHERE_ARTICLENAME_AND_KEYPHRASE_SQL";
		public static final String DELETE_FROM_GROUPINFO_SQL = "DELETE_FROM_GROUPINFO_SQL";
		public static final String RETRIVE_UNIQUE_ARTICLENAMES_FROM_LOGLIKELIHOOD_SQL = "RETRIVE_UNIQUE_ARTICLENAMES_FROM_LOGLIKELIHOOD_SQL";
		public static final String RETRIVE_GROUPID_FROM_DUPLICATEBUG_WHERE_ARTICLENAME_SQL = "RETRIVE_GROUPID_FROM_DUPLICATEBUG_WHERE_ARTICLENAME_SQL";
		public static final String INSERT_DUPLICATEARTICLE_SQL = "INSERT_DUPLICATEARTICLE_SQL";
		public static final String RETRIVE_ALLLOGIN_SQL = "RETRIVE_ALLLOGIN_SQL";
		public static final String RETRIVE_GROUPINGVIEW_SQL = "RETRIVE_GROUPINGVIEW_SQL";
		public static final String RETRIVE_LOGLIKELIHOOD_FULL_ORDERBY_SQL = "RETRIVE_LOGLIKELIHOOD_FULL_ORDERBY_SQL";
		public static final String RETRIVE_COMPARISION_FULL_SQL = "RETRIVE_COMPARISION_FULL_SQL";
		public static final String RETRIVE_ARTICLENAMES_SQL = "RETRIVE_ARTICLENAMES_SQL";
		public static final String INSERT_KEYWORDLIST_SQL = "INSERT_KEYWORDLIST_SQL";
		public static final String INSERT_AUTHOR_LIST_SQL = "INSERT_AUTHOR_LIST_SQL";
		public static final String RETRIVE_ALLCLEAN_ARTICLES_FOR_ARTICLENAME_SQL = "RETRIVE_ALLCLEAN_ARTICLES_FOR_ARTICLENAME_SQL";
		public static final String RETRIVE_ALLTOKENS_WHERE_ARTICLENAME_SQL = "RETRIVE_ALLTOKENS_WHERE_ARTICLENAME_SQL";
		public static final String RETRIVE_ALL_ARTICLES_SQL = "RETRIVE_ALL_ARTICLES_SQL";
		public static final String RETRIVE_ALL_ARTICLES_WHERE_USN_SQL = "RETRIVE_ALL_ARTICLES_WHERE_USN_SQL";
		public static final String RETRIVE_DINSTINCT_ARTICLENAMES_FROM_FV_SQL = "RETRIVE_DINSTINCT_ARTICLENAMES_FROM_FV_SQL";
		public static final String RETRIVE_DINSTINCT_ARTICLENAMES_FROM_FREQ_SQL = "RETRIVE_DINSTINCT_ARTICLENAMES_FROM_FREQ_SQL";
		public static final String RETRIVE_DINSTINCT_TOKENNAMES_FROM_FREQ_WHERE_ARTICLENAME_SQL = "RETRIVE_DINSTINCT_TOKENNAMES_FROM_FREQ_WHERE_ARTICLENAME_SQL";
		public static final String RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_SQL = "RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_SQL";
		public static final String RETRIVE_FREVO_FOR_ARTICLENAME_TOKENNAME_SQL_FROM_FREQ = "RETRIVE_FREVO_FOR_ARTICLENAME_TOKENNAME_SQL_FROM_FREQ";
		public static final String RETRIVE_FV_COMPUTATION_SQL = "RETRIVE_FV_COMPUTATION_SQL";
		public static final String RETRIVE_ARTICLE_FOR_ARTICLENAME_SQL = "RETRIVE_ARTICLE_FOR_ARTICLENAME_SQL";
		public static final String RETRIVE_FV_FOR_ARTICLENAME_TOKEN_SQL = "RETRIVE_FV_FOR_ARTICLENAME_TOKEN_SQL";
		public static final String INSERT_BESTFV_SQL = "INSERT_BESTFV_SQL";
		public static final String RANK_BESTFV_SQL = "RANK_BESTFV_SQL";
		public static final String DELETE_FROM_BESTFV_SQL = "DELETE_FROM_BESTFV_SQL";
		public static final String RETRIVE_KEYWORDS_WHERE_ARTICLENAME_SQL = "RETRIVE_KEYWORDS_WHERE_ARTICLENAME_SQL";
		public static final String RETRIVE_AUTHORS_WHERE_ARTICLENAME_SQL = "RETRIVE_AUTHORS_WHERE_ARTICLENAME_SQL";
		public static final String RETRIVE_FREQ_FROM_LOGLIKELIHOOD_WHERE_ARTICLENAME_AND_WORDSIN_SQL = "RETRIVE_FREQ_FROM_LOGLIKELIHOOD_WHERE_ARTICLENAME_AND_WORDSIN_SQL";
		public static final String RETRIVE_FREQ_FROM_FREQ_WHERE_ARTICLENAME_AND_TOKENNAME_SQL = "RETRIVE_FREQ_FROM_FREQ_WHERE_ARTICLENAME_AND_TOKENNAME_SQL";
		public static final String RETRIVE_ARTICLE_FOR_ARTICLEID_SQL = "RETRIVE_ARTICLE_FOR_ARTICLEID_SQL";
		public static final String RETRIVE_ARTICLEID_FOR_ARTICLENAME_SQL = "RETRIVE_ARTICLEID_FOR_ARTICLENAME_SQL";
		public static final String RETRIVE_UNIQUE_ARTICLENAMES_FOR_AUTHORS_IN_SQL = "RETRIVE_UNIQUE_ARTICLENAMES_FOR_AUTHORS_IN_SQL";
		public static final String RETRIVE_UNIQUE_ARTICLENAMES_FOR_KEYWORDS_IN_SQL = "RETRIVE_UNIQUE_ARTICLENAMES_FOR_KEYWORDS_IN_SQL";
		public static final String RETRIVE_UNIQUE_ARTICLENAMES_FOR_PUBLISHERS_IN_SQL = "RETRIVE_UNIQUE_ARTICLENAMES_FOR_PUBLISHERS_IN_SQL";

	}

	interface CONSTANTS {

		public static final String SPACE = "  ";

	}

	interface Message {

		public static final String MESSAGE_INTERNAL = "An internal has Ocuured. Please Contact System Administrator";
		public static final String ARTICLE_STORED_SUCESSFULLY = "Article Stored Sucessfully";
		public static final String ARTICLES_RETRIVE_SUCESSFUL = "Retrival of Articles are sucessful";
		public static final String NO_ARTCILES_FOUND = "No Articles Found";
		public static final String ARTICLE_FAILED = "Article Submission Failed";
		public static final String STORE_WEBDATA_SUCESS = "Article From Web Saved Sucessfully";
		public static final String WEBSITE_URL = "Web Site URL";
		public static final String NULL_ERROR_MSG = "cannot be Null";
		public static final String ARTICLE_NAME_EMPTY = "Article Name Cannot be Empty";

		/* Messages related to Stopwords */
		public static final String STOPWORD_REMOVE_FAILED = "Stopword Removal Failed";
		public static final String NO_STOPWORD_EXIST = "Stopword does not exist";
		public static final String STOPWORD_REMOVE_SUCESS = "Stopwords removed sucessfully";
		public static final String EMPTY_STOPWORD = "Stopword Cannot be Empty";
		public static final String STOPWORD_EXIST = "Stopword Already Exist";
		public static final String STOPWORD_ADD_FAILED = "Failed to Add Stop Word";
		public static final String STOPWORD_ADD_SUCESS = "Stop Word Added Sucessfully";
		public static final String EMPTY_STOPWORDS = "Stop Words are Empty";
		public static final String STOPWORD_SUCESS = "Retrival of Stop Words is sucessful";

		public static final String VIEW_ADMIN_SUCESS_PAGE = "adminsucess";
		public static final String VIEW_ADMIN_ERROR_PAGE = "adminerror";

		public static final String PATNAME_ALREADYEXIST = "Patient Name already Exists. You cannot have multiple appointments with doctor";
		public static final String HEART_ATTACK_ERR_MSG = "Heart Attack History Cannot be Empty";
		public static final String DIABETIC_ERR_MSG = "Please select Diabetic Information";
		public static final String BP_ERR_MSG = "Please select BP Information";
		public static final String OBESITY_ERR_MSG = "Please select Obesity Information";
		public static final String AGE_ERR_MSG = "Please select Age";
		public static final String SEX_ERR_MSG = "Please select Sex";
		public static final String DOB_ERR_MSG = "Please Select Date of Birth";
		public static final String COULD_NOT_MAINTAIN_HISTORY = "Could not Maintain History";
		public static final String AGEGROUP_ERRORMSG = "Age Group must has been selected";
		public static final String INCOME_ERRORMSG = "Income must has been selected";
		public static final String PROFEXP_ERRORMSG = "Profession Experience has been selected";
		public static final String CONTINUE_TEST = "Continue for the Test";
		public static final String ADMIN_CONTACT = "Please Contact System Administrator Call-9877777900";

		/* Data Cleaning and Tokenization Output */
		public static final String CLEANREVIEWS_SUCESS = "Clean of Articles is Sucessful";
		public static final String TOKENS_SUCESS = "Tokenization Process has been completed Sucessfully";
		public static final String CLEANREVIEWS_EMPTY = "Clean Articles are Empty";
		public static final String INSERT_TOKENS_FAILED = "Insertion of Tokens has Failed";
		public static final String EMPTY_TOKENS = "Tokens Are Empty";
		public static final String TOKENRETRIVAL_SUCESS = "Retrival of Tokens is Sucessful";
		public static final String EMPTY_REVIEWSLIST = "Empty Matrix has been Obtained";
		public static final String REVIEWS_FETCH_SUCESS = "Articles have been Fetched Sucessfully";
		public static final String CLEANMODEL_FAILED = "Cleaning of Articles has Failed";

		/* Key Phrase Messages */
		public static final String EMPTY_KEYPHRASE = "Key Phrase Cannot be Empty";
		public static final String KEYPHRASE_ADD_FAILED = "Key Phrase Could not be Added";
		public static final String KEYPHRASE_ADD_SUCESS = "Key Phrase Added Sucessfully";
		public static final String KEYPHRASE_EXIST = "Key Phrase Already exist";
		public static final String KEYPHRASE_RETRIVE_SUCESS = "Key Phrase Retrived Sucessfully";
		public static final String KEYPHRASE_REMOVE_SUCESS = "Key Phrase Removal is Sucessful";
		public static final String KEYPHRASE_REMOVE_FAILED = "Key Phrase Removal Failed";
		public static final String KEYPHRASE_NOT_EXIST = "Key Phrase Does not Exist";

		/* Phrase G */

		public static final String EMPTY_PHRASEG = "PhraseG Cannot be Empty";
		public static final String NOTYPE_TYPE_CANNOT_BE_SAME = "No Type and Type Cannot be Same";
		public static final String PHRASEG_ADD_FAILED = "Failed to Add Phrase G";
		public static final String PHRASEG_ADD_SUCESS = "PhraseG Added Sucessfully";
		public static final String PHRASEG_EXIST = "PhraseG Exist";
		public static final String PHRASEG_REMOVE_FAILED = "PhraseG Removal Failed";
		public static final String PHRASEG_REMOVE_SUCESS = "PhraseG Removal is sucessful";
		public static final String PHRASEG_NOT_EXIST = "PhraseG does not exist";
		public static final String PHRASEG_RETRIVE_SUCESS = "PhraseG Retrival Sucess";
		public static final String PHRASES_GENERATION_FAILED = "Phrases Generation Has Failed";
		public static final String PHRASES_GEN_SUCESS = "Phrases Generation is Sucessful";
		public static final String NO_CLEANARTICLES_FOUND = "No Clean Articles have been Found";

		/* Messages for Phrases */

		public static final String COULD_NOT_REMOVE_PHRASES = "Could not Remove Phrases";
		public static final String TOKENS_EMPTY = "No Tokens Found For Processing";
		public static final String STOPWORDS_EMPTY = "Stopwords Cannot be Empty";
		public static final String KEYPHRASES_EMPTY = "Key Phrases Cannot be Empty";
		public static final String PHRASEG_EMPTY = "Phrase G Cannot be Empty";
		public static final String NO_PHRASES_ARE_FOUND = "No Phrases are found";
		public static final String COULD_NOT_INSERT_PHRASES = "Could not Insert Phrases";
		public static final String EMPTY_PHRASES = "No Phrases Found";
		public static final String PHRASES_RETRIVAL_SUCESS = "Phrases Retrival is Sucessful";
		public static final String ADJECTIVE_GENERATION_FAILED = "Generation of Adjectives have Failed";
		public static final String ADJECTIVE_GEN_SUCESS = "Adjective Generation is Sucessful";
		public static final String REVIEW_LIST_EMPTY = "Review List is Empty";
		public static final String INSERTING_ADJECTIVES_FAILED = "Insertion of Adjectives have Failed";
		public static final String NO_ADJECTIVES_FOUND_IN_ARTICLE = "No Adjectives Found in Article";
		public static final String NO_KEYPHRASES_FOUND = "No Key Phrases Found";

		/* Strusture Messages */

		public static final String NO_STRUCTURE1_FOUND = "No Structure1 Keywords have been Found";
		public static final String STRUCTURE1_GENERATION_FAILED = "Structure1 generation Failed";
		public static final String STRUCTURE1_GEN_SUCESS = "Structure1 Generation is sucessful";
		public static final String NO_INSERTION_STRUCTURE1 = "Structure1 Could not be Inserted";
		public static final String STRUCTURE1_RETRIVAL_SUCESS = "Structure1 Retrival is Sucessful";
		public static final String EMPTY_STRUCTURE1 = "Structure1 is Empty";
		public static final String EMPTY_STRUCTURE2 = "Structure2 is Empty";
		public static final String EMPTY_STRUCTURE3 = "Structure3 is Empty";
		public static final String STRUCTURE2_RETRIVAL_SUCESS = "Structure2 Retrival is Sucessful";
		public static final String STRUCTURE3_RETRIVAL_SUCESS = "Structure3 Retrival is Sucessful";
		public static final String STRUCTURE2_GENERATION_FAILED = "Struture2 Generation Failed";
		public static final String STRUCTURE2_GEN_SUCESS = "Structure2 Generation is Sucessful";
		public static final String NO_INSERTION_STRUCTURE2 = "Struture2 Could not be Inserted";
		public static final String STRUCTURE3_GENERATION_FAILED = "Structure3 generation Failed";
		public static final String STRUCTURE3_GEN_SUCESS = "Structure3 Generation is Sucessful";
		public static final String DELETE_STRUCTURE2_FAILED = "Deletion of Struture2 has Failed";
		public static final String NO_INSERTION_STRUCTURE3 = "Insertion of Struture3 has Failed";
		public static final String NO_STRUCTURE3_FOUND = "Structure3 has Failed Sucessfully";
		public static final String DELETE_STRUCTURE3_FAILED = "Deletion of Structure3 Failed";
		public static final String NO_ADJECTIVES_FOUND = "No Adjectives Found";
		public static final String NO_STRUCTURE2_FOUND = "No Structure2 Found";
		public static final String NO_PHRASELIST_FOUND = "No Phrase List Found";
		public static final String FREQ_GENERATION_FAILED = "Frequency Generation has Failed";
		public static final String FREQ_GEN_SUCESS = "Frequency Generation is Sucessful";
		public static final String DELETE_FREQ_FAILED = "Deletion of Frequency has Failed";
		public static final String DELETE_TYPE_FAILED = "No Concepts Found";
		public static final String NO_REVIEWSMODEL_FOUND = "No Articles Found";
		public static final String RETRIVE_DIFFRENT_CONCEPTS_NONE = "Retrival of Concepts is Empty";
		public static final String RETRIVE_DIFFRENT_PHRASES_NONE = "Retrival of Diffrent Phrases is Empty";
		public static final String RETRIVE_DIFFRENT_ADJECTIVES_NONE = "Retrival of Diffrent Adjectives is Empty";
		public static final String RETRIVE_DIFFRENT_STRUCTURE1_NONE = "Retrival of Structure1 has Failed";
		public static final String RETRIVE_DIFFRENT_STRUCTURE2_NONE = "Retrival of Structure2 has Failed";
		public static final String RETRIVE_DIFFRENT_STRUCTURE3_NONE = "Retrival of Structure3 has Failed";

		/* Insert Freq Failed */
		public static final String INSERT_FREQ_FAILED = "Insertion of Frequency has Failed";
		public static final String EMPTY_ARTICLENAMES = "Article Names cannot be Empty";
		public static final String ARTICLENAMES_SUCESS = "Article Names retrival is Sucessful";
		public static final String EMPTY_COMPAREARTICLE_RESULT = "Comparision of Articles Could not be Done at this point";
		public static final String COMPARINGARTICLE_SUCESS = "Comparision of Articles is Sucessful";
		public static final String BOTH_ARTICLENAMES_CANNOT_BE_SAME = "Both the Articles are the Same";
		public static final String KEY_PHRASE_ARTICLE_NAME_EMPTY = "Key Phrase is Empty for Article Name = ";
		public static final String NO_TOKENS_FOR_ARTICLE_LEFT = "No Tokens have been Found for Article Name = ";
		public static final String NO_TOKENS_FOR_ARTICLE_RIGHT = "No Tokens Found for Article Right = ";
		public static final String NO_KEYPHRASES_FOR_LEFT_ARTICLE = "No Key Phrases for Left Article = ";
		public static final String NO_KEYPHRASES_FOR_RIGHT_ARTICLE = "No Key Phrases for Right Article = ";
		public static final String KEY_PHRASE_UNION_SET_EMPTY = "Key Pharse Union List is Empty";
		public static final String BOTH_ARTICLENAMES_ARE_SIMILAR = "Both Articles are Similar";
		public static final String BOTH_ARTICLENAMES_ARE_NOTSIMILAR = "Both Articles are not Similar";
		public static final String COULD_NOT_MEASURE_SIMILARITY = "Could not measure Similarity";

		String KEY_PHRASE_INTERSECTION_SET_EMPTY = "Key Phrase Intersection List is Empty";

		public static final String INTERNAL_ERROR = "Please Contact System Adminitrator. An Internal Error has Ocuurred";
		public static final String FIRSTNAME_EMPTY = "First Name cannot be Empty";
		public static final String LASTNAME_EMPTY = "Last Name cannot be Empty";
		public static final String USERID_EMPTY = "User ID Cannot be Empty";
		public static final String EMAIL_EMPTY = "Email Cannot be Empty";
		public static final String PASSWORD_EMPTY = "Password Cannot be Empty";
		public static final String USERREGISTERED_SUCESS_MSG = "User Has Registered Sucessfully.Please Login";
		public static final String USERALREADY_EXIST = "User Already Exist";
		public static final String NO_USER_EXISTS = "No User Already Exist";
		public static final String PASSWORD_WRONG = "Password does not exist";
		public static final String USER_LOGIN_SUCESS = "User Login is Sucessful";
		public static final String COULDNOT_DELETE_FREQUENCY = "Could not remove the old Frequency Matrix";
		public static final String COULDNOT_FIND_ARTICLES = "Please Perform Tokenization Step Before Frequency Computation";
		public static final String COULDNOT_FIND_TOKENS = "Could not Find Tokens for Article";
		public static final String COULDNOT_INSERT_FREQUENCY = "Could not Store Frequency Computation at this Point of Time";
		public static final String FREQ_COMPUTATION_SUCESS = "Frequency Computation is Sucessful";
		public static final String LIKELIHOOD_SUCESS = "Likelihood Computed Sucessfully";
		public static final String RVCOEFFCIENT_SUCESS = "RV Coeffcient Computed Sucessfully";
		public static final String KEY_INTERSECTION_SET_EMPTY = "Key List Intersection is Empty";
		public static final String CLUSTERING_SUCESSS = "CLustering is Sucessful";
		public static final String THEMES_NOT_FOUND = "Themes Not Found";
		public static final String USN_EMPTY = "USN Cannot be Empty";
		public static final String GENDER_EMPTY = "Gender Cannot be Empty";
		public static final String NAME_EMPTY = "Name Cannot  be Empty";
		public static final String ADDRESS_EMPTY = "Address Cannot be Empty";
		public static final String CITY_EMPTY = "City Cannot  be Empty";
		public static final String STATE_EMPTY = "State Cannot be Empty";
		public static final String PINCODE_EMPTY = "Pin Code Cannot be Empty";
		public static final String FATHERNAME_EMPTY = "Father Name Cannot be Empty";
		public static final String FATHERNUM_EMPTY = "Father Number Cannot be Empty";
		public static final String FATHEREMAIL_EMPTY = "Father Email Cannot be Empty";
		public static final String MOTHERNAME_EMPTY = "Mother Name Cannot be Empty";
		public static final String MOTHERNUM_EMPTY = "Mother Number Cannot be Empty";
		public static final String MOTHEREMAIL_EMPTY = "Mother Email Cannot be Empty";
		public static final String GUARDNAME_EMPTY = "Guardian Name Cannot be Empty";
		public static final String GUARDNUM_EMPTY = "Guardian Number Cannot be Empty";
		public static final String GUARDEMAIL_EMPTY = "Guardian Email Cannot be Empty";
		public static final String ADDTYPE_EMPTY = "Admission Type Cannot be Empty";
		public static final String CHALLAN_NUM_EMPTY = "Challan Number Cannot be Empty";
		public static final String FEEPAID_EMPTY = "Fee Paid Cannot be Empty";
		public static final String RESIDENTIALADDRESS_EMPTY = "Residential Address Cannot be Empty";
		public static final String RESIDENTIALSTATUS_EMPTY = "Residential Status Cannot be Empty";
		public static final String SEMMARKS_EMPTY = "Semester Marks Cannot be Empty";
		public static final String ACTIVITY_EMPTY = "Activity Cannot be Empty";
		public static final String SEMMARKS_NUMERIC = "Semester Marks must be Numeric";
		public static final String FEEPAID_NUMERIC = "Fees Paid Must be Numeric";
		public static final String CHALLANNUMBER_NUMERIC = "Challan Must be Numeric";
		public static final String INVALID_USER_PASSWORD = "Invalid Username(USN)/Password";
		public static final String DEGREE_EMPTY = "Degree Cannot be Empty";
		public static final String SPECIFICATION_EMPTY = "Specification Cannot be Empty";
		public static final String COLLEGE_EMPTY = "College Cannot be Empty";
		public static final String DEPART_EMPTY = "Department Cannot be Empty";
		public static final String SUBJECTS_EMPTY = "Subjects Cannot be Empty";
		public static final String ARTICLENAME_EMPTY = "Article Name Cannot be Empty";
		public static final String ARTICLEDESC_EMPTY = "Article Desc Cannot be Empty";
		public static final String PUBLISHER_EMPTY = "Publisher Cannot be Empty";
		public static final String KEYWORDS_EMPTY = "Keywords Cannot be Empty";
		public static final String FILENAME_EMPTY = "File Name Cannot be Empty";
		public static final String MULTIPARTFILE_EMPTY = "Multi Part File is Empty";
		public static final String CONTENTTYPE_EMPTY = "Content Type Cannot be Empty";
		public static final String FILECONTENTS_EMPTY = "File Contents Cannot be Empty";
		public static final String SAVE_ARTICLENAME = "Saving of Article is SUccessful";
		public static final String INSERT_ARTICLE_FAILED = "Insertion of Articles have Failed";
		public static final String DUPLICATE_ARTICLENAME = "Duplicate Article Name Exist";
		public static final String AUTHORS_EMPTY = "Authors Can be Empty";
		public static final String INSERT_KEYWORDLIST_FAILED = "Keyword Insertion has Failed";
		public static final String INSERT_AUTHOR_LIST_FAILED = "Insertion of Author List has Failed";
		public static final String CLEAN_ARTICLE_EMPTY = "No Clean Data Found for Article";
		public static final String SESSION_EXPIRED = "Session has Expired";
		public static final String NO_ARTICLES_FOUND = "No Articles have been Found";
		public static final String ARTICLES_RETRIVE_SUCESS = "Articles have been Retrived Sucessfully";
		public static final String FEATUREVECTOR_MESSAGE = "Feature Vector has been Computed Sucessfully";
		public static final String PLEASE_DO_FREQUENCY = "Please Upload Some Articles Before Performing Feature Vector";
		public static final String FEATURE_VECTOR_EMPTY = "Feature Vector Computation has Failed";
		public static final String EMPTY_FV = "Empty Feature Vector";
		public static final String FV_RETRIVAL_SUCESS = "Feature Vector Retrival is Sucessful";
		public static final String SEARCH_EMPTY = "Search Empty Cannot be Empty";
		public static final String SEARCHRESULTS_EMPTY = "Search Results are Empty";
		public static final String INSERT_LOGLIKELIHOOD = "Insertion of Log Likelihood has Failed";
		public static final String COULD_NOT_SAVE_ARTICLE = "Could not Save Article";
		public static final String FEATUREVECTOR_FAILED = "Feature Vector Computation has Failed";
		public static final String QUERY_EMPTY = "Query is Empty";
		public static final String NO_ARTICLES_FOUND_WITH_ADDITIONALCRITERIA = "No Articles Found with Additional Criteria";
		public static final String COULD_NOT_FIND_RESULTS = "No Search Results Found for the Criteria";
	}

	interface Views {

		public static final String APPLICATION_WELCOME_PAGE = "welcome";

		public static final String VIEW_P_WELCOMEPAGE = "user";

		public static final String WEBDATA_SUBMIT_INPUT = "documentsubmission";
		public static final String SUCESS_PAGE = "sucess";

		public static final String STOPWORD_INPUT = "addStopword";
		public static final String REMOVESTOPWORD_INPUT = "removeStopword";

		public static final String VIEW_ADMIN_SUCESS_PAGE = "adminsucess";
		public static final String VIEW_ADMIN_ERROR_PAGE = "adminerror";

		/* Pages for KeyPhrase */

		public static final String KEYPHRASEADD_INPUT = "addkeyphrase";
		public static final String REMOVE_KEYPHRASE_INPUT = "removekeyphrase";

		/* Pages for PhraseG */

		public static final String PHRASEG_ADD_INPUT = "addPhraseG";
		public static final String REMOVE_PHRASEG_INPUT = "removePhraseG";

		/* Pages for Pharses Generation */

		public static final String FAILURE_PAGE = "error";

		public static final String VIEW_REGISTER_INPUT = "registerview";
		public static final String VIEW_CUSTOMER_WELCOMEPAGE = "custwelcome";
		public static final String VIEW_LOGIN_INPUT = "login";
		public static final String VIEW_ADMIN_WELCOMEPAGE = "admin";

		public static final String VIEW_CUSTOMER_SUCESS_PAGE = "customersucess";

		public static final String VIEW_REGISTER_TEACH_INPUT = "registerviewteacher";

		public static final String VIEW_TEACH_WELCOMEPAGE = "teachwelcome";

		public static final String VIEW_ARTICLE_SUBMISSION_INPUT = "offlinearticlesubmission";

		public static final String VIEW_TEACHER_SUCESS_INPUT = "teachersucess";

		public static final String TEACHER_ARTICLE_DETAILS_PAGE = "teacherarticledetail";

		public static final String ADMIN_ARTICLE_DETAILS_PAGE = "adminarticledetail";

		public static final String SEARCH_INPUT = "search";
		
		public static final String SEARCH_INPUT_DELETE = "teacherarticles";

		public static final String LOGIN_INPUT = "login";

		public static final String STUDENT_ARTICLE_DETAILS_PAGE = "studentarticledetail";

	}

	interface Keys {
		public static final String OBJ = "obj";
		public static final String STOPWORDS_SYMBOL = "[^a-zA-Z]+";
		public static final String SPACE = "  ";
		public static final int ADMIN_TYPE = 1;
		public static final String LOGINID_SESSION = "LOGINID_SESSION";
		public static final String LOGINTYPE_SESSION = "LOGINTYPE_SESSION";
		public static final int CUSTOMER_TYPE = 4;
		public static final int TEACHER_TYPE = 2;

	}

	interface DatabaseColumns {

		public static final String REVIEWID_COL = "REVIEWID";

		public static final String REVIEWDETAILS_COL = "REVIEWDETAILS";

		public static final String STOPWORDID_COL = "STOPWORDID";

		public static final String STOPWORD_COL = "STOPWORD";

		public static final String CLEANID_COL = "CLEANID";

		public static final String TOKENID_COL = "TOKENID";

		public static final String TOKENNAME_COL = "TOKENNAME";

		public static final String FREQID_COL = "FREQID";

		public static final String FREQ_COL = "FREQ";

		public static final String ARTICLENAME_COL = "ARTICLENAME";

		public static final String TYPE_COL = "TYPE";

		public static final String KEYPHARSE_COL = "KEYPHARSE";

		public static final String KEYPHRASEID_COL = "KEYPHARSEID";

		public static final String PHRASE_COL = "PHRASE";

		public static final String PHRASEID_COL = "PHRASEID";

		public static final String ADJECTIVE_COL = "ADJECTIVE";

		public static final String ADJECTIVEID_COL = "ADJECTIVEID";

		public static final String STRUCTUREID_COL = "STRUCTUREID";

		public static final String STRUCTURE_COL = "STRUCTURE";

		String CLEANARTICLE_COL = "CLEANARTICLE";

		/* PhraseG */

		String PHARSEGID_COL = "PHARSEGID";
		String PHASEG_COL = "PHASEG";
		String NOTTYPE_COL = "NOTYPE";

		/* Columns for Freq Computation */

		String SENTENCEID_COL = "SENTENCEID";
		String FREQKEYPHARSE_COL = "FREQKEYPHARSE";
		String FREQPHRASE_COL = "FREQPHRASE";
		String FREQSTRUCTURE3_COL = "FREQSTRUCTURE3";
		String FREQADJECTIVE_COL = "FREQADJECTIVE";
		String FREQPHRASEG_COL = "FREQPHRASEG";
		String FREQSTRUCTURE1_COL = "FREQSTRUCTURE1";
		String FREQSTRUCTURE2_COL = "FREQSTRUCTURE2";

	}

	interface DatabaseColumnsKeys {

		String ARTICLENAME_KEY = "ARTICLENAME";
		String CLEANARTICLE_KEY = "CLEANARTICLE";
		String TOKENNAME_KEY = "TOKENNAME";
		String KEYPHARSE_KEY = "KEYPHARSE";
		String TYPE_KEY = "TYPE";

		/* PhraseG */

		String PHARSEGID_KEY = "PHARSEGID";
		String PHASEG_KEY = "PHASEG";
		String NOTTYPE_KEY = "NOTYPE";

		/* Columns for Freq Computation KEy */

		String SENTENCEID_KEY = "SENTENCEID";
		String FREQKEYPHARSE_KEY = "FREQKEYPHARSE";
		String FREQPHRASE_KEY = "FREQPHRASE";
		String FREQSTRUCTURE3_KEY = "FREQSTRUCTURE3";
		String FREQADJECTIVE_KEY = "FREQADJECTIVE";
		String FREQPHRASEG_KEY = "FREQPHRASEG";
		String FREQSTRUCTURE1_KEY = "FREQSTRUCTURE1";
		String FREQSTRUCTURE2_KEY = "FREQSTRUCTURE2";
		/* Phrase */

		String PHRASE_KEY = "PHRASE";
		String ADJECTIVE_KEY = "ADJECTIVE";

		/* Struture1, Structure2 and Structure3 */

		String STRUCTURE_KEY = "STRUCTURE";
		String KEYPHARSELIST_KEY = "KEYPHARSELIST";

	}

	interface DATABASESQL {
		public static final String RETRIVE_REGISTER_USERIDS_SQL = "RETRIVE_REGISTER_USERIDS_SQL";
		public static final String INSERT_LOGIN_SQL = "INSERT_LOGIN_SQL";
		public static final String RETRIVE_PASSWORD_WHERE_USERID_SQL = "RETRIVE_PASSWORD_WHERE_USERID_SQL";
		public static final String RETRIVE_LOGINTYPE_WHERE_USERID_SQL = "RETRIVE_LOGINTYPE_WHERE_USERID_SQL";
		public static final String INSERT_ACTIVITY_SQL = "INSERT_ACTIVITY_SQL";
		public static final String INSERT_SUBJECT_SQL = "INSERT_SUBJECT_SQL";
		public static final String INSERT_FEATUREVO_SQL = "INSERT_FEATUREVO_SQL";

	}

}
