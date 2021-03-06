package com.ab.telugumoviequiz.main;

import android.os.Bundle;

public interface Navigator {
    String CURRENT_GAMES = "CurrentGames";
    String ENROLLED_GAMES = "EnrolledGames";

    String MIXED_GAMES_VIEW = "MixedGamesView";
    String MIXED_ENROLLED_GAMES_VIEW = "MixedEnrolledGamesView";
    String CELEBRITY_GAMES_VIEW = "CelebrityGamesView";
    String CELEBRITY_ENROLLED_GAMES_VIEW = "CelebrityEnrolledGames";
    String QUESTION_VIEW = "QuestionView";

    String HISTORY_VIEW = "HistoryView";
    String WALLET_VIEW = "WalletView";
    String CHAT_VIEW = "ChatView";
    String REFERRALS_VIEW = "ReferralsView";
    String TRANSACTIONS_VIEW = "TransactionsView";
    String WITHDRAW_REQ_VIEW = "WithdrawReqsView";
    String PROFILE_VIEW = "ProfileView";

    void launchView(String viewId, Bundle params, boolean storeState);
}
