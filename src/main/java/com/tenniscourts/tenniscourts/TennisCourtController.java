package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.schedules.ScheduleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Api(value = "TennisCourtController" , tags = {"Tennis court Controller"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/tennis-court")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @ApiOperation(value = "Add a new tennis court")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tennis court created")})
    @PostMapping
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiOperation(value = "Find a tennis court by id. Optional withSchedule parameter to get the tennis court with schedules")
    @GetMapping("/{id}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long id, @RequestParam(required = false, value = "withSchedule") boolean withSchedule) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(id, withSchedule));
    }

    @ApiOperation(value = "Find a tennis courts")
    @GetMapping
    public ResponseEntity<List<TennisCourtDTO>> findAllTennisCourt() {
        return ResponseEntity.ok(tennisCourtService.findAllTennisCourts());
    }

}