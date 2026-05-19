package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;

// disclaimer: generated with AI based on API response
@JsonIgnoreProperties(ignoreUnknown = true)
public record Lot(
        Long id,
        String title,
        String subtitle,
        String thumbImageUrl,
        String originalImageUrl,
        Integer favoriteCount,
        String url,
        Boolean localized,
        String translatedTitle,
        String translatedSubtitle,
        Long auctionId,
        String pubnubChannel,
        Boolean useRealtimeMessageFallback,
        Boolean isContentExplicit,
        Boolean reservePriceSet,
        OffsetDateTime biddingStartTime,
        BuyNow buyNow,
        Boolean hasFreeShipping,
        Boolean isVectorSearchResult
) {}