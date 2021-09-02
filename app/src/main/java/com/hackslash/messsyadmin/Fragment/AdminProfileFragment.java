package com.hackslash.messsyadmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackslash.messsyadmin.Model.UserClass;
import com.hackslash.messsyadmin.R;

import org.w3c.dom.Text;

import java.util.Objects;

public class AdminProfileFragment extends Fragment {
    private ImageView profileImageIV;    // IV stands for imageview
    private TextView nameTV, emailTV ;
    DocumentReference docref ;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    UserClass user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_profile,container,false);

           profileImageIV = (ImageView)view.findViewById(R.id.profile_image);
           nameTV = (TextView) view.findViewById(R.id.name);
           emailTV = (TextView) view.findViewById(R.id.email);


             String url = "https://th.bing.com/th/id/OIP.vxVLwAKkFacSqbweyL_-twAAAA?pid=ImgDet&w=280&h=280&rs=1";
            Glide.with(Objects.requireNonNull(getContext())).load(url).into(profileImageIV);


    firebaseAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        docref = FirebaseFirestore.getInstance().collection("UserInformation").document(currentUser.getUid());

        Glide.with(Objects.requireNonNull(getContext())).load(url).into(profileImageIV);

        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                     user = documentSnapshot.toObject(UserClass.class);

                   if(user != null) {
                       nameTV.setText(user.getsName());
                       emailTV.setText(user.getsEmail());
                       String sImageUrl = user.getImageUrl();
                       if(sImageUrl != null && !(sImageUrl.equalsIgnoreCase("null"))){
                       Glide.with(Objects.requireNonNull(getContext())).load(sImageUrl).into(profileImageIV);}
                   }

                }else{
                    Toast.makeText(getActivity(), "Information doesn't exists ", Toast.LENGTH_SHORT).show();
                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        return view;

    }
}
