package com.example.jyotirmay.contactapplication.com.example.jyotirmay.contactapplication.datasource;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jyotirmay.contactapplication.ContactDetailsActivity;
import com.example.jyotirmay.contactapplication.R;

import java.util.List;

/**
 * Created by Krishnendu on 12-Feb-18.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    DbHandler handler;
    private List<DataBean> dataBeans;
    public static String ID="ID";
    Context context;

    public ContactAdapter(List<DataBean> contactBean, Context context)
    {
        dataBeans=contactBean;
        this.context=context;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item, parent, false);
        ContactViewHolder viewHolder =new ContactViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        DataBean bean=dataBeans.get(position);
        holder.nameView.setText(bean.getName());
        String name=bean.getName();
        char firstLetter=name.charAt(0);
        holder.firstLetterView.setText(firstLetter+"");
        holder.idView.setText(bean.getC_id());

    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView nameView, idView, firstLetterView;
        ConstraintLayout layout;

        public ContactViewHolder(View itemView) {
            super(itemView);
            nameView=itemView.findViewById(R.id.tvName);
            idView=itemView.findViewById(R.id.tvID);
            firstLetterView=itemView.findViewById(R.id.tvFirst);

            layout=itemView.findViewById(R.id.constraintView);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id=idView.getText().toString();
                    Intent intent=new Intent(context, ContactDetailsActivity.class);
                    intent.putExtra(ID, id);
                    context.startActivity(intent);
                }
            });
        }
    }
}
