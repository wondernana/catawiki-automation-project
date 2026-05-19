
package models;

public record Lot(
    long id,
    String title,
    String subtitle,
    String thumbImageUrl,
    String originalImageUrl,
    int favoriteCount,
    String url,
    boolean localized,
    String translatedTitle,
    String translatedSubtitle,
    long auctionId,
    String pubnubChannel,
    boolean useRealtimeMessageFallback,
    boolean use_realtime_message_fallback,
    boolean isContentExplicit,
    boolean reservePriceSet,
    String biddingStartTime,
    String bidding_start_time,
    String buyNow,
    Boolean hasFreeShipping,
    boolean isVectorSearchResult
) {}
