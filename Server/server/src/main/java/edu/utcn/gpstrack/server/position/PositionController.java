/* package edu.utcn.gpstrack.server.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Radu Miron
 * @version 1
 */

/*
@RestController
@RequestMapping("/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping
    public Position create(@RequestBody Position position) {
        return positionService.create(position);
    }
}
*/

package edu.utcn.gpstrack.server.position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author radu.miron
 * @since 18.10.2016
 */
@RestController
@RequestMapping(value = "/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public Position putPosition(@RequestBody Position position) {
        return positionService.save(position);

    }

    @RequestMapping(method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Position> readPositionFromTerminal(@RequestParam("terminalId") String terminalId , @RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date stDate = simpleDateFormat.parse(startDate);
        Date enDate = simpleDateFormat.parse(endDate);

        return positionService.readPositionFromTerminal(terminalId,stDate,enDate);
    }


    @GetMapping("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Position UpdatePosition(@PathVariable Long id, @RequestBody Position position) {
        if(id==position.getId()){
            Position position1=positionService.save(position);
            position1.setId(id);
            return position1;
        }
        else return null;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public Position createPosition(@RequestBody Position p) {
        return positionService.save(p);
    }

    @RequestMapping(value = "/createPositionForMobile", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Position createPosition(@RequestParam("latitude") String latitude,
                                   @RequestParam("longitude") String longitude,
                                   @RequestParam("terminalId") String terminalId) {

        Position pos = new Position();
        pos.setLatitude(latitude);
        pos.setLongitude(longitude);
        pos.setTerminalId(terminalId);

        return positionService.save(pos);
    }


    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    @Produces(MediaType.APPLICATION_JSON)
    public void deletePosition(@PathVariable Long  id) {
        positionService.deletePositionService(id);
    }




}