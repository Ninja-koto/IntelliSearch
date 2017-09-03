package com.dao.inter;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.model.AdjectiveVO;
import com.model.ArticleVO;
import com.model.BestFeatureVector;
import com.model.CleanArticleModel;
import com.model.CompareArticleRVVO;
import com.model.ComparisionVO;
import com.model.DuplicateBugResult;
import com.model.FeatureVO;
import com.model.FreqComputation;
import com.model.FrequencyVO;
import com.model.GroupingView;
import com.model.KeyPhraseModel;
import com.model.LogLikelihoodVO;
import com.model.PhraseGModel;
import com.model.PhraseVO;
import com.model.RegisterUser;
import com.model.ArticleModel;
import com.model.ArticleModelObj;
import com.model.SearchCriteria;
import com.model.SearchObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.StructureVO;
import com.model.TokenVO;

public interface ContextualDAOIF {

	public StatusInfo insertReview(ArticleModel reviewModel);

	public List<ArticleModel> retriveAllReviews();

	StatusInfo insertStopWord(String stopWord);

	List<String> retriveStopWordsOnly();

	List<StopWordsVO> retriveStopWords();

	public StatusInfo removeStopword(String stopWord);

	public List<TokenVO> retriveAllTokens();

	public List<CleanArticleModel> retriveCleanReviews();

	public StatusInfo deleteAllCleanReviews();

	public StatusInfo insertCleanDetails(CleanArticleModel cleanReview);

	public StatusInfo insertToken(TokenVO tokenVO);

	public StatusInfo deleteAllTokens();

	public List<String> retriveKeyPhraseOnly(String type);

	public StatusInfo insertKeyPhrase(String keyphrase, String type);

	public List<KeyPhraseModel> retriveKeyPhrases();

	public StatusInfo removeKeyPhrase(String keyphrase, String type);

	StatusInfo insertPhraseG(PhraseGModel pharaseGModel);

	List<PhraseGModel> retrivePhraseGPhrases();

	StatusInfo removePhraseGForPhraseGTypeNoType(PhraseGModel phraseGModel);

	List<String> retrivePhraseGWhereTypeAndNoType(PhraseGModel pharaseGModel);

	public StatusInfo deleteAllPhrases();

	public List<String> retriveTokensOnly();

	public List<String> retriveAllKeyPhraseOnly();

	public List<String> retriveAllPhraseGOnly();

	public StatusInfo insertPhrasesList(Set<String> phrases);

	public List<PhraseVO> retrivePhraseList();

	public StatusInfo deleteAllAdjectives();

	public StatusInfo insertAdjectives(List<String> adjectiveList);

	public List<AdjectiveVO> retriveAllFullAdjectives();

	public List<String> retriveAllAdjectives();

	public StatusInfo insertStruture1(List<String> structureList);

	public List<StructureVO> retriveStructure1List();

	public StatusInfo deleteAllStructure1();

	public List<StructureVO> retriveStructure2List();

	public List<StructureVO> retriveStructure3List();

	public StatusInfo deleteAllStructure2();

	public StatusInfo insertStruture2(List<String> structureList);

	public List<String> retrivePhraseListOnly();

	public StatusInfo deleteAllStructure3();

	public StatusInfo insertStruture3(List<String> structureList);

	public StatusInfo deleteAllFreq();

	public List<String> retriveDiffrentConcepts();

	public List<String> retriveAllKeyPhraseOnlyForConcept(String type);

	public List<String> retriveAllStructure1Only();

	public List<String> retriveAllStructure2Only();

	public List<String> retriveAllStructure3Only();

	public List<String> retrivePhraseGWhereTYPEISNOT(String type);

	public StatusInfo insertFreqComputation(FreqComputation freqComputation);

	public List<FreqComputation> viewFreq();

	public List<String> retriveDistincetTokensForArticleName(
			String articleNameLeft);

	public List<String> retriveKeyPhraseOnlyForTokensAndType(
			List<String> tokenNamesLeft, String typeCombo);

	public int retriveCountForTokenAndArticleName(String word,
			String articleNameLeft);

	public int findNoOfTokensForArticleName(String articleNameLeft);

	public List<FreqComputation> retriveFreqComputationForArticleName(
			String articleNameLeft);

	public StatusInfo insertAdjectiveNLP(String str1);

	public List<String> retriveAdjectivesForNLP();

	public List<AdjectiveVO> retriveAllFullNLPAdjectives();

	public List<FreqComputation> retriveFreqComputationForArticleNameAndType(
			String articleNameLeft, String typeCombo);

	public int retriveLoginType(String userId);

	public List<String> retriveUserIds();

	public String retrivePassword(String userId);

	StatusInfo insertLogin(RegisterUser registerUser);

	public StatusInfo insertFrequency(FrequencyVO freqVO);

	List<String> retriveAllUniqueArticleNamesFromTokenization();

	List<String> retriveDistinctTokensForArticleName(String articleName);

	int retriveCountForTokenInArticle(String tokenTemp, String articleName);

	public List<String> retriveUniqueArticleNamesFromFrequency();

	public List<String> retriveUniqueTokensFromFrequencyForArticle(
			String articleName);

	public List<FrequencyVO> retriveUniqueTokensInfoFromFrequencyForArticle(
			String articleName);

	public int computeFreqOtherTokensForNotTokenNameAndArticleName(
			String tokenName, String articleName);

	public int computeTotalFreq(String articleName);

	public StatusInfo insertLogLikelihood(
			List<LogLikelihoodVO> logLikeliHoodVOList);

	public List<LogLikelihoodVO> retriveLogLikelihoodFromDB();

	public List<Double> computeFreqListForArticleNameAndTokenName(
			String articleName, String tokenName);

	public boolean deleteLogLikeLihood();

	public List<String> retriveDistincetTokensForArticleNameFromLogLikelihood(
			String articleNameLeft);

	public List<Integer> retriveFreqValuesForArticleNameFromLogLikelihood(
			String articleNameLeft);

	public StatusInfo storeComparison(CompareArticleRVVO compareArticleVO);

	public List<Integer> retriveFreqValuesForArticleNameFromLogLikelihoodAndCommonWords(
			String articleNameLeft, List<String> keyPhraseIntersection);

	public StatusInfo deleteDuplicateArticles();

	public List<String> retriveUniqueArticleNamesFromLogLikelihood();

	public int retriveGroupIdForArticleNameFromDuplicity(String articleName);

	public StatusInfo insertDuplicateArticle(
			DuplicateBugResult duplicateBugResult);

	public StatusInfo insertGroupedArticle(DuplicateBugResult duplicateBugResult);

	public int retriveGroupIdForArticleNameFromGroupId(String articleName1);

	public List<RegisterUser> retriveAllRegisterInfo();

	public List<GroupingView> viewGrouping();

	public List<LogLikelihoodVO> retriveLogLikelihoodFromDBOrderBy(
			Double thresholdTemp);

	public List<ComparisionVO> retriveComparison();

	public StatusInfo insertActivityList(RegisterUser registerUser);

	public StatusInfo insertSubjectList(RegisterUser registerUser);

	public List<String> retriveArticleNames();

	public StatusInfo insertArticle(ArticleVO articleVO);

	public StatusInfo insertAuthorList(List<String> authorList,
			String articleName);

	StatusInfo insertKeyWordList(List<String> keyWordList, String artilceName);

	public List<CleanArticleModel> retriveCleanDataForArticleName(
			String articleName);

	public List<TokenVO> retriveAllTokensForArticleName(String articleName);

	public List<ArticleVO> retriveAllArticles();

	public List<ArticleVO> retriveAllArticlesForCustomer(String loginId);

	public List<String> retriveDistinctArticleNamesForFreq();

	List<String> retriveDistinctArticleNamesFromFeatureVector();

	public List<String> retriveDistinctTokensFromFreqForArticleName(
			String articleName);

	public int retriveCountOfDocsInWhichTokenIsPresent(String tokenName);

	public FrequencyVO retriveFreqForArticleNameAndTokenName(
			String articleName, String tokenName);

	public StatusInfo insertFeatureVector(FeatureVO featureVO);

	public List<FeatureVO> viewFV();

	public ArticleVO retriveArticleDetailForArticleName(String articleName);

	public List<Double> retriveFVForArticleNameAndTokenName(
			String distinctArticleName, String tokenTemp);

	public StatusInfo insertBestFV(List<BestFeatureVector> bestFV);

	public List<BestFeatureVector> rateArticles();

	public StatusInfo deleteFromBestFV();

	public List<String> retriveKeywordsForArticles(String articleName);

	public List<String> retriveAuthorListForArticles(String articleName);

	public List<Integer> retriveFreqValuesForArticleNameFromLogLikelihoodANDWordsIN(
			String articleNameLeft, List<String> keyPhraseIntersection);

	public List<String> retriveDistincetTokensForArticleNameFromFrequeny(
			String articleNameLeft);

	public List<Integer> retriveFreqValuesForArticleNameFromFreqANDWordsIN(
			String articleNameLeft, List<String> keyPhraseIntersection);

	public ArticleVO retriveArticleDetailForArticleId(String articleName);
	
	public StatusInfo deleteArticleDetailForArticleId(String articleName);

	public int retriveArticleIdForArticleName(String articleName);

	public List<String> retriveUniqueArticleNamesFromAuthorList(
			List<String> authors);
 
	public List<String> retriveUniqueArticleNamesFromKeywordList(List<String> keywordArrayList);

	public List<String> retriveUniqueArticleNamesFromPublisherList(
			List<String> publishers);  
	
	public List<String> getAllInterestArea(String interestArea);

	public int retriveCountOfDocsInWhichArticleIsPresent(String area);

	public int retriveFreqForArticleName(String area);

	public Set<String> getAllPublisher(String trim);

	public int retriveCountOfDocsInWhichPublisherIsPresent(String area, String publisherName);

	public int retriveFreqForArticleNameWithPublisher(String area);

	public Set<String> getAllAuthors(String trim);

	public int retriveCountOfDocsInWhichAuthorsIsPresent(String area,
			String trim);

	public Set<String> getAllKeyWord(String keywords);

	public int retriveCountOfDocsInWhichKeywordsIsPresent(String area,
			String keywords);

	public StatusInfo insertSearchHistory(List<Map<String, Object>> searchHistoryList);

	public List<SearchObj> getHistoryByUSN(String uSN);

}
