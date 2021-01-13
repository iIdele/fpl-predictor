// Create Adapter to dynamically output Players and Predictions in ListView

package dcu.ie.stefano.puzzuoli2.fplpredictor;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * LAdapter is the entity we'll be using to display Player/Predictions in a list.
 */
public class LAdapter extends BaseAdapter implements Filterable {
    private Context context;                                // To store Context of activity to use in
    private ArrayList<Prediction> rPreds;                   // To store Players/Predictions
    private ArrayList<Prediction> rFilterList;              // To store filtered Players/Predictions
    private CustomFilter filter;                            // Custom Filter

    LAdapter(Context c, ArrayList<Prediction>preds) {
        this.context = c;
        this.rPreds = preds;
        this.rFilterList = preds;
    }

    // Get number of Players/Predictions in rPreds Arraylist
    @Override
    public int getCount() {
        return rPreds.size();
    }

    @Override
    public Prediction getItem(int position) {
        return rPreds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rPreds.indexOf(getItem(position));
    }

    // Get View to display Player/Predictions in list
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);

        TextView playerName = row.findViewById(R.id.playerName);
        TextView playerPreds = row.findViewById(R.id.playerPrediction);

        // Set resources on view
        String index = Integer.toString(position+1) + ". ";
        playerName.setText(String.format("%s%s",index,rPreds.get(position).getPlayer_name()));
        playerPreds.setText(Integer.toString(rPreds.get(position).getPoints_prediction()));

        return row;
    }

    // Get filter to apply to lists
    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter = new CustomFilter();
        }

        return filter;
    }

    // Filter list to create filtered sub lists
    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Prediction> filters = new ArrayList<Prediction>();

                for (int i=0;i< rFilterList.size();i++){
                    if (rFilterList.get(i).getPlayer_name().toUpperCase().contains(constraint)){
                        Prediction p = rFilterList.get(i);
                        filters.add(p);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }
            else{
                results.count=rFilterList.size();
                results.values=rFilterList;
            }
            return results;
        }

        // Change original list to new filtered list
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            rPreds = (ArrayList<Prediction>) results.values;
            notifyDataSetChanged();
        }
    }
}