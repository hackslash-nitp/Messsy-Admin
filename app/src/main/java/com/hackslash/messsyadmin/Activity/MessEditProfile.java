package com.hackslash.messsyadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackslash.messsyadmin.R;

import java.io.IOException;

public class MessEditProfile extends AppCompatActivity {
    Button saveDetails,backBtn;
    Dialog dialog;
    private TextView nameTV , emailTV , passwordTV;
    private static final int PICK_IMAGE = 1;
    private ImageView img2;
    Uri imageUri;
    Bitmap bitmap;
    String sName, sPassword ,sEmail ,generatedFilePath;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    FirebaseAuth firebaseAuth ;
    FirebaseUser currentUser;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_edit_profile);
        backBtn=findViewById(R.id.mess_editProfile_backBtn);
        img2 = (ImageView)findViewById(R.id.ivEditProfileMessMember);
        nameTV = (TextView) findViewById(R.id.etName);
        emailTV = (TextView) findViewById(R.id.etEmail);
        passwordTV = (TextView) findViewById(R.id.etPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        documentReference = FirebaseFirestore.getInstance().collection("UserInformation").document(currentUser.getUid());
        storageReference = storage.getReference().child("images").child(currentUser.getUid());





        Button AddImage;
        AddImage = (Button)findViewById(R.id.addImage);


        saveDetails=findViewById(R.id.saveDetails);
        dialog =new Dialog(this);


        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = nameTV.getText().toString();
                sEmail = emailTV.getText().toString();
                sPassword = passwordTV.getText().toString();



                if(sName.isEmpty()){
                    nameTV.setError("Write new name");
                    return;
                }
                if(sEmail.isEmpty()){
                    emailTV.setError("Write new email");
                    return;
                }
                if(sPassword.isEmpty()){
                    passwordTV.setError("Write new password");
                    return;
                }

                currentUser.updateEmail(sEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MessEditProfile.this, "Email Updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MessEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                currentUser.updatePassword(sPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MessEditProfile.this, "Password Updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MessEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                documentReference.update("sName",sName).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MessEditProfile.this, "Name updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MessEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                documentReference.update("sEmail",sEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MessEditProfile.this, "Email updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MessEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                if(imageUri != null) {
                    storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MessEditProfile.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    generatedFilePath = uri.toString();
                                    documentReference.update("imageUrl",generatedFilePath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MessEditProfile.this, "Image Url Saved On Firebase", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MessEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MessEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MessEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }




                openDialog();
            }
        });
        if(generatedFilePath != null){
            Glide.with(this).load(generatedFilePath).into(img2);}
        else{
            Glide.with(this).load("https://cdn3.iconfinder.com/data/icons/avatars-round-flat/33/avat-01-512.png").into(img2);

        }



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }

        });
    }

    private void openDialog() {
        dialog.setContentView(R.layout.successfully_edited_profile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button Done = dialog.findViewById(R.id.btn_Done);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK)
        {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                img2.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
