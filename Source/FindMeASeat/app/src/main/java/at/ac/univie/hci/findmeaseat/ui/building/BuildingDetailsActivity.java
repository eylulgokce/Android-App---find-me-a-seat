package at.ac.univie.hci.findmeaseat.ui.building;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import at.ac.univie.hci.findmeaseat.R;
import at.ac.univie.hci.findmeaseat.model.booking.BookingService;
import at.ac.univie.hci.findmeaseat.model.booking.BookingServiceFactory;
import at.ac.univie.hci.findmeaseat.model.booking.Period;
import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusService;
import at.ac.univie.hci.findmeaseat.model.booking.status.SeatStatusServiceFactory;
import at.ac.univie.hci.findmeaseat.model.building.Area;
import at.ac.univie.hci.findmeaseat.model.building.Building;
import at.ac.univie.hci.findmeaseat.model.building.Seat;
import at.ac.univie.hci.findmeaseat.model.building.service.BuildingService;
import at.ac.univie.hci.findmeaseat.model.building.service.BuildingServiceFactory;
import at.ac.univie.hci.findmeaseat.model.user.favorite.FavoriteService;
import at.ac.univie.hci.findmeaseat.model.user.favorite.FavoriteServiceFactory;
import at.ac.univie.hci.findmeaseat.ui.DatePickerHandler;
import at.ac.univie.hci.findmeaseat.ui.TimePickerHandler;
import at.ac.univie.hci.findmeaseat.ui.buildings.AreaDetailsActivity;

import static java.lang.String.format;

public class BuildingDetailsActivity extends AppCompatActivity {

    public static final String BUILDING_ID_EXTRA_NAME = "buildingId";

    private final BuildingService buildingService = BuildingServiceFactory.getSingletonInstance();
    private final FavoriteService favoriteService = FavoriteServiceFactory.getSingletonInstance();
    private final SeatStatusService seatStatusService = SeatStatusServiceFactory.getSingletonInstance();
    private final BookingService bookingService = BookingServiceFactory.getSingletonInstance();

    private Building building;
    private AreasAdapter areasAdapter;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime endDate = LocalDateTime.now().plusHours(1);

    private final DatePickerHandler startDatePickerHandler = new DatePickerHandler(this, this.startDate, dateFormatter, updatedDate -> {
        startDate = updatedDate;
        updateFreeSeats();
    });
    private final DatePickerHandler endDatePickerHandler = new DatePickerHandler(this, this.endDate, dateFormatter, updatedDate -> {
        endDate = updatedDate;
        updateFreeSeats();
    });
    private final TimePickerHandler startTimePickerHandler = new TimePickerHandler(this, this.startDate, timeFormatter, updatedDate -> {
        startDate = updatedDate;
        updateFreeSeats();
    });
    private final TimePickerHandler endTimePickerHandler = new TimePickerHandler(this, this.endDate, timeFormatter, updatedDate -> {
        endDate = updatedDate;
        updateFreeSeats();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details);

        UUID buildingId = UUID.fromString(getIntent().getStringExtra(BUILDING_ID_EXTRA_NAME));
        this.building = buildingService.getBuildingById(buildingId);
        setTitle(building.getName());

        // Date pickers
        EditText startDateEditText = findViewById(R.id.from_date);
        EditText endDateEditText = findViewById(R.id.to_date);
        EditText startTimeEditText = findViewById(R.id.from_time);
        EditText endTimeEditText = findViewById(R.id.to_time);

        startDatePickerHandler.setDateView(startDateEditText);
        endDatePickerHandler.setDateView(endDateEditText);
        startTimePickerHandler.setDateView(startTimeEditText);
        endTimePickerHandler.setDateView(endTimeEditText);

        // Area list
        final ListView areasListView = findViewById(R.id.area_list);
        this.areasAdapter = new AreasAdapter(this, building.getAllAreas(), seatStatusService, getPeriod());
        areasListView.setAdapter(areasAdapter);
        areasListView.setOnItemClickListener((parent, view, position, id) -> startAreaDetailsActivity(building.getAllAreas().get(position)));

        ImageButton favoriteButton = findViewById(R.id.favorite_button);

        if (favoriteService.isFavorite(building)) {
            displayAsFavorite(favoriteButton);
        } else {
            displayAsNotFavorite(favoriteButton);
        }

        favoriteButton.setOnClickListener(v -> {
            if (favoriteService.isFavorite(building)) {
                displayAsNotFavorite(favoriteButton);
                Toast.makeText(this, "Von Startseite entfernt", Toast.LENGTH_SHORT).show();
                favoriteService.removeFromFavorites(building);
            } else {
                displayAsFavorite(favoriteButton);
                Toast.makeText(this, "Zur Startseite hinzugefügt", Toast.LENGTH_SHORT).show();
                favoriteService.addToFavorites(building);
            }
        });

        updateFreeSeats();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFreeSeats();
    }

    public void performQuickBooking(View view) {
        try {
            bookingService.bookAnySeat(building, getPeriod());
            updateFreeSeats();
            Toast.makeText(this, "Sitz wurde gebucht", Toast.LENGTH_LONG).show();
        } catch (Throwable exception) {
            Toast.makeText(this, "Keine Plätze mehr frei", Toast.LENGTH_LONG).show();
        }

    }

    private Period getPeriod() {
        return new Period(startDate, endDate);
    }

    private void displayAsFavorite(ImageButton favoriteButton) {
        favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
    }

    private void displayAsNotFavorite(ImageButton favoriteButton) {
        favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
    }

    private void updateFreeSeats() {
        TextView seats = findViewById(R.id.seats_view);
        List<Seat> freeSeats = seatStatusService.getFreeSeats(building, getPeriod());
        seats.setText(format(Locale.getDefault(), "%d / %d", freeSeats.size(), building.maximalSeats()));
        areasAdapter.updatePeriod(getPeriod());
        areasAdapter.notifyDataSetChanged();
    }

    private void startAreaDetailsActivity(Area area) {
        Intent intent = new Intent(this, AreaDetailsActivity.class);
        intent.putExtra(AreaDetailsActivity.BUILDING_ID_EXTRA_NAME, building.getId().toString());
        intent.putExtra(AreaDetailsActivity.AREA_NAME_EXTRA_NAME, area.getName());
        intent.putExtra(AreaDetailsActivity.START_DATE_EXTRA_NAME, getPeriod().getStart().toString());
        intent.putExtra(AreaDetailsActivity.END_DATE_EXTRA_NAME, getPeriod().getEnd().toString());
        startActivity(intent);
    }

}
