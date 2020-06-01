package at.ac.univie.hci.findmeaseat.ui.buildings;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
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

import static java.time.LocalDateTime.parse;

public class AreaDetailsActivity extends AppCompatActivity implements SeatsAdapter.SeatSelectionHandler {

    public static final String BUILDING_ID_EXTRA_NAME = "buildingId";
    public static final String AREA_NAME_EXTRA_NAME = "areaName";
    public static final String START_DATE_EXTRA_NAME = "startDate";
    public static final String END_DATE_EXTRA_NAME = "endDate";

    private final BuildingService buildingService = BuildingServiceFactory.getSingletonInstance();
    private final SeatStatusService seatStatusService = SeatStatusServiceFactory.getSingletonInstance();
    private final BookingService bookingService = BookingServiceFactory.getSingletonInstance();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private RecyclerView.Adapter seatsAdapter;
    private Seat selectedSeat;

    private Period period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_details);
        UUID buildingId = UUID.fromString(getIntent().getStringExtra(BUILDING_ID_EXTRA_NAME));
        String areaName = getIntent().getStringExtra(AREA_NAME_EXTRA_NAME);
        Building building = buildingService.getBuildingById(buildingId);
        period = new Period(parse(getIntent().getStringExtra(START_DATE_EXTRA_NAME)), parse(getIntent().getStringExtra(END_DATE_EXTRA_NAME)));
        TextView periodView = findViewById(R.id.seat_booking_period);
        periodView.setText(String.format("%s - %s", period.getStart().format(dateFormatter), period.getEnd().format(dateFormatter)));
        Area area = building.getArea(areaName);
        setTitle(String.format("%s - %s", area.getName(), area.getBuilding().getName()));
        RecyclerView seatsRecyclerView = findViewById(R.id.seatsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 5);
        seatsRecyclerView.setLayoutManager(layoutManager);
        seatsAdapter =
                new SeatsAdapter(area.getAllSeats(), this, this, seatStatusService, period);
        seatsRecyclerView.setAdapter(seatsAdapter);
    }

    @Override
    public boolean isSelected(Seat seat) {
        return seat.equals(selectedSeat);
    }

    @Override
    public void select(Seat seat) {
        if (seatStatusService.getStatus(seat, period).equals(SeatStatusService.SeatStatus.OCCUPIED)) return;
        selectedSeat = seat;
        seatsAdapter.notifyDataSetChanged();
        TextView selectedSeatTextView = findViewById(R.id.selected_seat_text_view);
        selectedSeatTextView.setText(String.format("Auswahl: %s", seat.getName()));
    }

    public void bookSelectedSeat(View view) {
        if(selectedSeat == null) return;
        bookingService.bookSeat(selectedSeat, period);
        selectedSeat = null;
        seatsAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sitz wurde gebucht", Toast.LENGTH_LONG).show();
    }

}
