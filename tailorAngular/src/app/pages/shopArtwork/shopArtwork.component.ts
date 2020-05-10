import { Component, OnInit } from '@angular/core';
import { ArtworkServiceService } from '../../services/artwork-service.service';
import { Artwork } from '../../class/artwork';
import { Router, ActivatedRoute } from '@angular/router';
import { SessionServiceService } from '../../services/session-service.service';
import { CustomerServiceService } from 'app/services/customer-service.service';
import { Tag } from 'app/class/tag';

@Component({
  selector: 'app-shopArtwork',
  templateUrl: './shopArtwork.component.html',
  styleUrls: ['./shopArtwork.component.css']
})
export class ShopArtworkComponent implements OnInit {

    artworks : Artwork[];
    errorMessage: string;
    tags: Tag[];

    constructor(private artworkService: ArtworkServiceService,
                private customerService: CustomerServiceService,
                private sessionService: SessionServiceService,
                private router: Router,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
      this.artworkService.getArtworks().subscribe(
        response => {
          this.artworks = response.artworks;
          this.sessionService.setArtworks(response.artworks);
          console.log(this.artworks.toString());
        },
        error => {
          this.errorMessage = error
        }
      );
      this.customerService.getAllTags().subscribe(
        response => {
          this.tags = response.tags
        },
        error => {
          this.errorMessage = error
        }
      );
    }

    viewArtwork(artworkID: number) {
        this.router.navigate(["pages/viewArtworkDetails/" + artworkID]);
    }
}
