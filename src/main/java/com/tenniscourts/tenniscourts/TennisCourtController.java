package com.tenniscourts.tenniscourts;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Tennis Court")
@RestController
@AllArgsConstructor
@RequestMapping("/tenniscourt")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @PostMapping
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @GetMapping(value="{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping(value="{tennisCourtId}/withschedules")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }

    @GetMapping(value="all")
    public ResponseEntity<List<TennisCourtDTO>> getAll() {
        return ResponseEntity.ok(tennisCourtService.getAll());
    }

}
