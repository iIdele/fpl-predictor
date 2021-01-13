package dcu.ie.stefano.puzzuoli2.fplpredictor;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

/**
 * Prediction is the entity we'll be using to store information about Players/Predictions.
 */
public class Prediction
{
    private Integer points_prediction = 0;          // To store point prediction value
    private String player_name = "";                // To store player name
    private String objectId = "";                   // To store object ID
    private String ownerId = "";                    // To store creator/owner ID
    private Date created = new Date();              // To store information on Date created
    private Date updated = new Date();              // To store information on last Date updated



    public Date getUpdated()
    {
        return updated;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public Integer getPoints_prediction()
    {
        return points_prediction;
    }

    public void setPoints_prediction( Integer points_prediction )
    {
        this.points_prediction = points_prediction;
    }

    public String getPlayer_name()
    {
        return player_name;
    }

    public void setPlayer_name( String player_name )
    {
        this.player_name = player_name;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public Date getCreated()
    {
        return created;
    }


    public Prediction save()
    {
        return Backendless.Data.of( Prediction.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Prediction> callback )
    {
        Backendless.Data.of( Prediction.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Prediction.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Prediction.class ).remove( this, callback );
    }

    public static Prediction findById( String id )
    {
        return Backendless.Data.of( Prediction.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Prediction> callback )
    {
        Backendless.Data.of( Prediction.class ).findById( id, callback );
    }

    public static Prediction findFirst()
    {
        return Backendless.Data.of( Prediction.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Prediction> callback )
    {
        Backendless.Data.of( Prediction.class ).findFirst( callback );
    }

    public static Prediction findLast()
    {
        return Backendless.Data.of( Prediction.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Prediction> callback )
    {
        Backendless.Data.of( Prediction.class ).findLast( callback );
    }

    public static List<Prediction> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Prediction.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Prediction>> callback )
    {
        Backendless.Data.of( Prediction.class ).find( queryBuilder, callback );
    }

}