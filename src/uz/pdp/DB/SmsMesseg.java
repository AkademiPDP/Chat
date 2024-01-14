package uz.pdp.DB;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SmsMesseg {

    private UUID FromId;
    private UUID toId;
    private String Text;
    private ZonedDateTime zonedDateTime;

    public SmsMesseg(UUID fromId, UUID toId, String text, ZonedDateTime zonedDateTime) {
        FromId = fromId;
        this.toId = toId;
        Text = text;
        this.zonedDateTime = zonedDateTime;
    }

    public String getText() {
        return Text;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public UUID getFromId() {

        return FromId;
    }
    public UUID getToId() {
        return toId;
    }





}
