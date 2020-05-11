package com.example.collegeevent;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<Message_Model>         messageList;
    FirebaseAuth mAuth;
    //    LinearLayout                        linearLayout;
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CollegeChatGroup").child("IseDept123456789").child("Chats");
    public static final int MSG_TYPE_LEFT             = -1;
    public static final int MSG_TYPE_CENTER            = 0;
    public static final int MSG_TYPE_RIGHT            = 1;
    //FirebaseUser firebaseUser;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //        private String               mesaage_content;
//        private String               sent_date_time;
//        private String               senders_name;
//        private String               senders_profile_pic;
//        private String               senders_unique_id;
//        private String               seen_by;                     // todo: later
//
//        private String               date_changed;
//        private String               group_joined_by;
//        private String               group_left_by;
//        private String               new_group_admin;
        public TextView             outgoingMsgContent;
        public TextView             outgoingmsgTime;
        public ImageView            outgoingmsgSent;
        public ImageView            outgoingmsgRecieved;

        public TextView             incomingMsgContent;
        public TextView             incomingmsgTime;
        public ImageView            incomingmsgSent;
        public ImageView            incomingmsgRecieved;

        public TextView             leftJoinDateAdminRemovedText;
        public ImageView            sendersProfileImage;
        public TextView             sendersName;

        public MyViewHolder(View view) {
            super(view);
            outgoingMsgContent              = view.findViewById(R.id.t_Message_out);
            incomingMsgContent              = view.findViewById(R.id.t_Message_in);
            outgoingmsgTime                 = view.findViewById(R.id.t_time_out);
            incomingmsgTime                 = view.findViewById(R.id.t_time_in);
            sendersProfileImage             = view.findViewById(R.id.incoming_profile_image);
            leftJoinDateAdminRemovedText    = view.findViewById(R.id.left_join_admin_date_textView);
            sendersName                     = view.findViewById(R.id.t_senders_name);
            outgoingmsgSent                 = view.findViewById(R.id.o_sent);
            outgoingmsgRecieved             = view.findViewById(R.id.o_recieved);
            incomingmsgRecieved             = view.findViewById(R.id.i_received);
            incomingmsgSent                 = view.findViewById(R.id.i_sent);
        }
    }
    public MessageAdapter(List<Message_Model> message_modelList) {
        this.messageList = message_modelList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        View itemView;
        if      (viewType == MSG_TYPE_LEFT){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.incoming_message_layout, parent, false);
        }
        else if (viewType == MSG_TYPE_CENTER){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.left_join_date_text_layout, parent, false);
        }
        else {

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.outgoing_message_layout, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Message_Model message = messageList.get(position);
        //this fn puts the data onto the each view holder.

//        if(!message.getDate_changed().equals("0"))
//            holder.leftJoinDateAdminRemovedText.setText(message.getDate_changed());
//        else if (message.getGroup_joined_by()!= null && !message.getGroup_joined_by().equals("0"))
//            holder.leftJoinDateAdminRemovedText.setText(message.getGroup_joined_by()+" joined the group");
//        else if (message.getGroup_left_by()!= null && !message.getGroup_left_by().equals("0"))
//            holder.leftJoinDateAdminRemovedText.setText(message.getGroup_left_by()+" left the group");
//        else if (message.getAdmin_name_who_removed_the_participant()!= null && message.getAdmin_removed_participant_with_name()!=null && (!message.getAdmin_name_who_removed_the_participant().equals("0") && !message.getAdmin_removed_participant_with_name().equals("0")))
//            holder.leftJoinDateAdminRemovedText.setText(message.getAdmin_name_who_removed_the_participant()+" removed "+message.getAdmin_removed_participant_with_name());
//        else if (message.getNew_group_admin() != null && !message.getNew_group_admin().equals("0"))
//            holder.leftJoinDateAdminRemovedText.setText(message.getNew_group_admin()+" is now the admin");
//        else if (message.getSenders_unique_id()!= null && message.getSenders_unique_id().equals( "1"/*firebaseUser.getUid()*/)){
//            holder.outgoingMsgContent.setText(message.getMessage_content());
//            holder.outgoingmsgTime.setText(message.getSent_date_time());
//        }
//        else {
            holder.incomingMsgContent.setText(message.getMessage_content());
            holder.incomingmsgTime.setText(message.getSent_date_time());
            holder.sendersName.setText(message.getSenders_name());
            holder.sendersProfileImage.setImageURI(Uri.parse(message.getSenders_profile_pic()));
            //}
            //holder.sendersProfileImage.setText(user.getSenders_profile_pic());

//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(v.getContext(), "Opening! Please wait.....", Toast.LENGTH_LONG).show();
//                if(flag == 1)
//                {
//                    flag = 0;
//                    holder.t_description.setVisibility(View.VISIBLE);
//                    holder.description.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    flag = 1;
//                    holder.t_description.setVisibility(View.GONE);
//                    holder.description.setVisibility(View.GONE);
//
//                }
//            }
//        });
//        }
    }
    @Override
    public int getItemViewType(int position) {
       // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(messageList.get(position).getSenders_unique_id().equals(/*mAuth.getUid()*/ "1" /*firebaseUser.getUid()*/))
            return MSG_TYPE_RIGHT;
//        else if (messageList.get(position).getMesaage_content().equals("0") &&
//                messageList.get(position).getSenders_unique_id().equals("0") &&
//                messageList.get(position).getSenders_profile_pic().equals("0") &&
//                messageList.get(position).getSent_date_time().equals("0") &&
//                messageList.get(position).getSenders_name().equals("0")){
//            return MSG_TYPE_CENTER;
//        }
        else
            return MSG_TYPE_LEFT;
    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }
}