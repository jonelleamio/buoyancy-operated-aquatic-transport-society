package com.kata.boats.infrastructure.in;

import com.kata.boats.application.BrowseBoatsUseCase;
import com.kata.boats.application.UndockBoatUseCase;
import com.kata.boats.application.RegisterBoatUseCase;
import com.kata.boats.application.UpdateBoatInfoUseCase;
import com.kata.boats.application.ViewBoatDetailsUseCase;
import com.kata.boats.domain.model.BoatStringId;
import com.kata.boats.infrastructure.in.dto.BoatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/boats")
@RequiredArgsConstructor
public class BoatController {

    private final BrowseBoatsUseCase browseBoatsUseCase;
    private final ViewBoatDetailsUseCase viewBoatDetailsUseCase;
    private final RegisterBoatUseCase registerBoatUseCase;
    private final UpdateBoatInfoUseCase updateBoatInfoUseCase;
    private final UndockBoatUseCase undockBoatUseCase;

    @GetMapping
    public CollectionModel<EntityModel<BoatDto>> browseAllBoats() {
        List<EntityModel<BoatDto>> boats = browseBoatsUseCase.execute().stream()
                .map(BoatDto::from)
                .map(EntityModel::of)
                .toList();

        return CollectionModel.of(boats,
                linkTo(methodOn(BoatController.class).browseAllBoats()).withSelfRel());
    }

    @GetMapping("/{boatId}")
    public EntityModel<BoatDto> viewBoatDetails(@PathVariable String boatId) {
        BoatDto boat = BoatDto.from(viewBoatDetailsUseCase.execute(BoatStringId.from(boatId)));

        return EntityModel.of(boat,
                linkTo(methodOn(BoatController.class).viewBoatDetails(boatId)).withSelfRel(),
                linkTo(methodOn(BoatController.class).browseAllBoats()).withRel("boats")
        );
    }

    @PostMapping
    public ResponseEntity<EntityModel<BoatDto>> registerBoat(@RequestBody BoatDto boatDto) {
        BoatDto registered = BoatDto.from(registerBoatUseCase.execute(BoatDto.toBoatRegistration(boatDto)));

        EntityModel<BoatDto> model = EntityModel.of(registered,
                linkTo(methodOn(BoatController.class).viewBoatDetails(registered.id().toString())).withSelfRel(),
                linkTo(methodOn(BoatController.class).browseAllBoats()).withRel("boats"),
                linkTo(methodOn(BoatController.class).undockBoat(registered.id().toString())).withRel("undock")
        );

        return ResponseEntity.created(linkTo(methodOn(BoatController.class).viewBoatDetails(registered.id().toString())).toUri())
                .body(model);
    }

    @PutMapping
    public ResponseEntity<EntityModel<BoatDto>> updateBoat(@RequestBody BoatDto boatDto) {
        BoatDto updated = BoatDto.from(updateBoatInfoUseCase.execute(BoatDto.toBoatInfoUpdate(boatDto)));

        EntityModel<BoatDto> model = EntityModel.of(updated,
                linkTo(methodOn(BoatController.class).viewBoatDetails(updated.id().toString())).withSelfRel(),
                linkTo(methodOn(BoatController.class).browseAllBoats()).withRel("boats"),
                linkTo(methodOn(BoatController.class).undockBoat(updated.id().toString())).withRel("undock")
        );

        return ResponseEntity.created(linkTo(methodOn(BoatController.class).viewBoatDetails(updated.id().toString())).toUri())
                .body(model);
    }

    @DeleteMapping("/{boatId}")
    public ResponseEntity<Void> undockBoat(@PathVariable String boatId) {
        undockBoatUseCase.execute(BoatStringId.from(boatId));
        return ResponseEntity.noContent().build();
    }
}
