package com.crio.shorturl;
import java.util.Random;
import java.util.HashMap;



public class ShortUrlImpl implements ShortUrl {
    HashMap<String,String> ShortTolong=new HashMap<>();
    HashMap<String,String> LongToshort=new HashMap<>();
    HashMap<String,Integer> hits=new HashMap<>();

    public String registerNewUrl(String longUrl){

        if(LongToshort.containsKey(longUrl)){
            return LongToshort.get(longUrl);
        }

        String shrtURL="http://short.url/";
       
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 9;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

            shrtURL+=generatedString;

            LongToshort.put(longUrl,shrtURL);
            ShortTolong.put(shrtURL,longUrl);

            return shrtURL;

    }

    public String registerNewUrl(String longUrl, String shortUrl){
        if(ShortTolong.containsKey(shortUrl)){
            return null;
        }

        LongToshort.put(longUrl,shortUrl);
        ShortTolong.put(shortUrl,longUrl);
        return shortUrl;
       
    }

    public String getUrl(String shortUrl){
        if(ShortTolong.containsKey(shortUrl)){
                Integer count = hits.get(ShortTolong.get(shortUrl));
                if (count == null) {
                    hits.put(ShortTolong.get(shortUrl), 1);
                }
                else {
                    hits.put(ShortTolong.get(shortUrl), count + 1);
                }
            return ShortTolong.get(shortUrl);
        }
        
        else return null;
    }

    public Integer getHitCount(String longUrl){
        if(hits.containsKey(longUrl))
        return hits.get(longUrl);
        return 0;
    }  
    
    public String delete(String longUrl){
        ShortTolong.remove(LongToshort.get(longUrl));
        String deleted=LongToshort.remove(longUrl);
        return deleted;

    }

}