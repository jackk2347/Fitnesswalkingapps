package hk.edu.hkmu.fitnesswalkingtracksapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DataListAdapter extends ArrayAdapter<Data> {
    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView title;
        TextView District;
        TextView Route;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public DataListAdapter(Context context, int resource, ArrayList<Data> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data information
        String title = getItem(position).getTitle();
        String district = getItem(position).getDistrict();
        String route = getItem(position).getRoute();

        //Create the data object with the information
        Data data = new Data(title,district,route);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.District = (TextView) convertView.findViewById(R.id.District);
            holder.Route = (TextView) convertView.findViewById(R.id.Route);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.title.setText(data.getTitle());
        holder.District.setText(data.getDistrict());
        holder.Route.setText(data.getRoute());


        return convertView;
    }



}
