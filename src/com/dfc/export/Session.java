package com.dfc.export;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.IDfLoginInfo;

 
public class Session {
    public static IDfSessionManager createSessionManager(String repo, String user, String pass) throws Exception {
        IDfClientX clientx = new DfClientX();
        IDfClient client = clientx.getLocalClient();
        IDfSessionManager sMgr = client.newSessionManager();
        IDfLoginInfo loginInfoObj = clientx.getLoginInfo();
        loginInfoObj.setUser(user);
        loginInfoObj.setPassword(pass);
        loginInfoObj.setDomain(null);
        sMgr.setIdentity(repo, loginInfoObj);
        System.err.println("Session Created..!");
        return sMgr;
    }
 
    public static void main(String[] args){
    	IDfSession session = null;
    	
    	String strFolderPath ="/Operations/Test";
    	String destDir ="C:\\Arun";
    	String srcFileOrDir = "C:\\import";
    	String destFolderPath ="/Operations";
    	String repo = "NABARDUAT";
    	String user = "dmadmin";
    	String pass = "demo.demo";
    	
    	try {
    	session = createSessionManager(repo, user, pass).getSession(repo);
    	//BulkExport.doExportMultipleObjects(strFolderPath, destDir, session);
    	BulkImport.doImport(srcFileOrDir, destFolderPath, session);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		if (session != null) {
                IDfSessionManager sMgr = session.getSessionManager();
                sMgr.release(session);
                System.err.println("Session Released..!");
    	}
    	
    	
    	}

	
}
}
 