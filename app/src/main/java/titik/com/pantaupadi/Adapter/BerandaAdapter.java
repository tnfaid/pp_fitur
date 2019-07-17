
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import titik.com.pantaupadi.DetailPenyakitDaunActivity;
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
        // Load image from internet and set it into imageView using Glide
        final BerandaModel beranda = listBeranda.get(position);
        Glide.with(mContext)
                .load(beranda.getGambar())
                .into(holder.mGambar);

        holder.mNamaPenyakit.setText(beranda.getNama_penyakit());
        holder.mAuthor.setText(beranda.getPenulis());
        holder.mTanggalPost.setText(beranda.getTanggal_upload());
        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext.getApplicationContext(), "Detail " + position+ "alamat :" + orderList.get(position).getAddress(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext.getApplicationContext(), DetailPenyakitDaunActivity.class);
                //parcelabel
                intent.putExtra("id",listBeranda.get(position).getId());
                intent.putExtra("nama_penyakit",listBeranda.get(position).getNama_penyakit());
                intent.putExtra("tanggal_upload",listBeranda.get(position).getTanggal_upload());
                intent.putExtra("penulis",listBeranda.get(position).getPenulis());
                intent.putExtra("kondisi",listBeranda.get(position).getKondisi());
                intent.putExtra("solusi",listBeranda.get(position).getSolusi());
                intent.putExtra("gambar",listBeranda.get(position).getGambar());

                mContext.startActivity(intent);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder{
//        CardView mCardView;
        TextView mNamaPenyakit;
        TextView mAuthor;
        TextView mTanggalPost;
        CardView mDetail;
        ImageView mGambar;


        public ViewHolder(View itemView) {
            super(itemView);
//            mCardView = itemView.findViewById(R.id.beranda_penyakit);
            mNamaPenyakit = itemView.findViewById(R.id.tv_nama_penyakit);
            mAuthor = itemView.findViewById(R.id.tv_author);
            mTanggalPost = itemView.findViewById(R.id.tv_tanggal_unggah);
            mDetail = itemView.findViewById(R.id.beranda_penyakit);
            mGambar = itemView.findViewById(R.id.imageView);
        }
    }

    public void clear(){
        listBeranda.clear();
        notifyDataSetChanged();
    }

}
