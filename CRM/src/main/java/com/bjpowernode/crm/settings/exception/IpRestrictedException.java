package com.bjpowernode.crm.settings.exception;

public class IpRestrictedException extends LoginException{
    public IpRestrictedException() {
        super();
    }

    public IpRestrictedException(String message) {
        super(message);
    }
}
