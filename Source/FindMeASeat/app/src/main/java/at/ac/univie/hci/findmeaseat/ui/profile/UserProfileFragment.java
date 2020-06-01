package at.ac.univie.hci.findmeaseat.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.user.AuthenticationService;
import at.ac.univie.hci.findmeaseat.model.user.AuthenticationServiceFactory;
import at.ac.univie.hci.findmeaseat.model.user.User;
import at.ac.univie.hci.findmeaseat.ui.login.LoginActivity;

public class UserProfileFragment extends Fragment {

    private final AuthenticationService authenticationService = AuthenticationServiceFactory.getSingletonInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);
        User user = authenticationService.getAuthenticatedUser();
        TextView firstName = root.findViewById(R.id.first_name_value);
        TextView lastName = root.findViewById(R.id.last_name_value);
        TextView email = root.findViewById(R.id.email_value);
        TextView matriculationNumber = root.findViewById(R.id.matriculation_number_value);
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        matriculationNumber.setText(user.getMatriculationNumber());
        root.findViewById(R.id.logout_button).setOnClickListener(this::logout);
        return root;
    }

    private void logout(View view) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

}
