package org.java.service;

import java.sql.Blob;

public interface PolicyService {

    int addOrder( Blob blob, String policyid);
}
