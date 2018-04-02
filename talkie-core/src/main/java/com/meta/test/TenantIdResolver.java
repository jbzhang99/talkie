package com.meta.test;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 * create by lhq
 * create date on  18-3-2下午4:08
 *
 * @version 1.0
 **/
public class TenantIdResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        return null;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
