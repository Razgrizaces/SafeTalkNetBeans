package com.drexel.duca.backend;

import java.util.Scanner;

import com.google.gson.Gson;
import com.temboo.Library.Facebook.OAuth.FinalizeOAuth;
import com.temboo.Library.Facebook.OAuth.FinalizeOAuth.FinalizeOAuthInputSet;
import com.temboo.Library.Facebook.OAuth.FinalizeOAuth.FinalizeOAuthResultSet;
import com.temboo.Library.Facebook.OAuth.InitializeOAuth;
import com.temboo.Library.Facebook.OAuth.InitializeOAuth.InitializeOAuthInputSet;
import com.temboo.Library.Facebook.OAuth.InitializeOAuth.InitializeOAuthResultSet;
import com.temboo.Library.Facebook.Reading.User;
import com.temboo.Library.Facebook.Reading.User.UserInputSet;
import com.temboo.Library.Facebook.Reading.User.UserResultSet;
import com.temboo.Library.Facebook.Searching.FQL;
import com.temboo.Library.Facebook.Searching.FQL.FQLInputSet;
import com.temboo.Library.Facebook.Searching.FQL.FQLResultSet;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;
import com.temboo.outputs.Facebook.FacebookUser;

public class Facebook {
	
	private TembooSession session;
	private InitializeOAuthResultSet initializeOAuthResults;
	
	public String startOAuth() throws TembooException {
		session = new TembooSession("Razgriz", "SafeTalk",
				"wEdaH1w5LdKnQHXCIMv6");
		InitializeOAuth initializeOAuthChoreo = new InitializeOAuth(session);

		// Get an InputSet object for the choreo
		InitializeOAuthInputSet initializeOAuthInputs = initializeOAuthChoreo.newInputSet();

		// Set inputs
		initializeOAuthInputs.set_AppID("196659050499139");

		// Execute Choreo
		initializeOAuthResults = initializeOAuthChoreo.execute(initializeOAuthInputs);
		
		return initializeOAuthResults.get_AuthorizationURL();
	}
	
	
	public String finishOAuth() throws TembooException {
		FinalizeOAuth finalizeOAuthChoreo = new FinalizeOAuth(session);

		// Get an InputSet object for the choreo
		FinalizeOAuthInputSet finalizeOAuthInputs = finalizeOAuthChoreo.newInputSet();

		// Set inputs
		finalizeOAuthInputs.set_CallbackID(initializeOAuthResults.get_CallbackID());
		finalizeOAuthInputs.set_AppSecret("96c5d4b9d54eff85e1e04168171859b0");
		finalizeOAuthInputs.set_AppID("196659050499139");

		// Execute Choreo
		FinalizeOAuthResultSet OAuthResults = finalizeOAuthChoreo.execute(finalizeOAuthInputs);
		
		return OAuthResults.get_AccessToken();
	}
	

}
