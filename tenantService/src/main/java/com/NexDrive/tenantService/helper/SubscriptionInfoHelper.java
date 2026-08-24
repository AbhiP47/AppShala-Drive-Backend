package com.NexDrive.tenantService.helper;

import com.NexDrive.tenantService.enums.SubscriptionPlanTier;

public class SubscriptionInfoHelper {

    private static final long GB = 1024L * 1024 * 1024;

    public static int getSubscriptionSeatLimit(SubscriptionPlanTier tier) {

        return switch (tier) {
            case FREE -> 10;
            case PRO -> 50;
            case BUSINESS -> 100;
            case ENTERPRISE -> 500;
        };
    }

    public static long getSubscriptionStorageQuotaBytes(SubscriptionPlanTier tier)
    {
        return switch (tier) {
            case FREE -> 100 * GB;
            case PRO -> 500 * GB;
            case BUSINESS -> 1000 * GB;
            case ENTERPRISE -> 5000 * GB;
        };
    }
}
