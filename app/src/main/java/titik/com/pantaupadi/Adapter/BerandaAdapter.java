
package titik.com.pantaupadi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import titik.com.pantaupadi.LoginActivity;
import titik.com.pantaupadi.MainActivity;
import titik.com.pantaupadi.Menu.Beranda;
import titik.com.pantaupadi.Model.BerandaModel;
import titik.com.pantaupadi.R;

import static titik.com.pantaupadi.LoginActivity.TAG_FIRST_NAME;
import static titik.com.pantaupadi.LoginActivity.TAG_LAST_NAME;

public class BerandaAdapter extends RecyclerView.Adapter<BerandaAdapter.ViewHolder> {

    private Context mContext;
    private List<BerandaModel> listBeranda;
    View mView;

    public BerandaAdapter(Context mContext, ArrayList<BerandaModel> orderList) {
        this.mContext = mContext;
        this.listBeranda = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_beranda, parent, false);
        ViewHolder vh = new ViewHolder(mView); // pass the view to View Holder
        return vh;
    }

    public int getItemCount() {
        return listBeranda.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mNamaPenyakit.setText(listBeranda.get(position).getJenis_tanaman());
        holder.mAuthor.setText(listBeranda.get(position).getPenulis());
        holder.mTanggalPost.setText(listBeranda.get(position).getTanggal_upload());
        //holder.mDateOrder.setText(orderList.get(position).getDateOrder());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), MainActivity.class);

                mContext.startActivity(intent);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        TextView mNamaPenyakit;
        TextView mAuthor;
        TextView mTanggalPost;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.beranda_penyakit);
            mNamaPenyakit = itemView.findViewById(R.id.tv_nama_penyakit);
            mAuthor = itemView.findViewById(R.id.tv_author);
            mTanggalPost = itemView.findViewById(R.id.tv_tanggal_unggah);
        }
    }

    public void clear(){
        listBeranda.clear();
        notifyDataSetChanged();
    }

}



//package titik.com.pantaupadi.Adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import titik.com.pantaupadi.MainActivity;
//import titik.com.pantaupadi.Menu.Beranda;
//import titik.com.pantaupadi.Model.BerandaModel;
//import titik.com.pantaupadi.R;
//
//public class BerandaAdapter extends RecyclerView.Adapter<BerandaAdapter.ViewHolder> {
//
//    private Context mContext;
//    private List<BerandaModel> listBeranda;
//    View mView;
//
//    public BerandaAdapter(Context mContext, ArrayList<BerandaModel> orderList) {
//        this.mContext = mContext;
//        this.listBeranda = orderList;
//
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_beranda, parent, false);
//        ViewHolder vh = new ViewHolder(mView); // pass the view to View Holder
//        return vh;
//    }
//
//    public int getItemCount() {
//        return listBeranda.size();
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.mNama.setText(listBeranda.get(position).get);
//        holder.mTanggal.setText(listBeranda.get(position).getTanggal());
//        holder.mAuthor.setText(listBeranda.get(position).getAuthor());
//        //holder.mDateOrder.setText(orderList.get(position).getDateOrder());
//        holder.mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext.getApplicationContext(), MainActivity.class);
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder{
//        TextView mNama;
//        ImageView mGambar;
//        TextView mTanggal;
//        TextView mAuthor;
//        CardView mCardView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mNama = itemView.findViewById(R.id.tv_nama_penyakit);
//            mGambar = itemView.findViewById(R.id.imageView);
//            mTanggal = itemView.findViewById(R.id.tv_tanggal_unggah);
//            mAuthor = itemView.findViewById(R.id.tv_author);
//            mCardView = itemView.findViewById(R.id.beranda_penyakit);
//        }
//    }
//
//    public void clear(){
//        listBeranda.clear();
//        notifyDataSetChanged();
//    }
//
//}
