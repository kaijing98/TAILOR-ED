import { Component, OnInit } from '@angular/core';
import { ArtworkServiceService } from 'app/services/artwork-service.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CustomerServiceService } from 'app/services/customer-service.service';
import { Offences } from 'app/class/offences';
import { Artwork } from 'app/class/artwork';
import { SessionServiceService } from 'app/services/session-service.service';

@Component({
  selector: 'app-filter-artwork',
  templateUrl: './filter-artwork.component.html',
  styleUrls: ['./filter-artwork.component.css']
})
export class FilterArtworkComponent implements OnInit {
  tagId: number;
  offences: Offences[];
  errorMessage: string;
  filteredArtwork: Artwork[];

  constructor(private artworkService: ArtworkServiceService,
    private router: Router,
    private customerService: CustomerServiceService,
    private activatedRoute: ActivatedRoute,
    private sessionService: SessionServiceService) {
      
     }

  ngOnInit(): void {
    this.tagId = parseInt(this.activatedRoute.snapshot.paramMap.get('tagId'));
    console.log('tagId: retrieve: ' + this.tagId);

    this.customerService.getAllOffences().subscribe(
      response => {
        this.offences = response.offences
      },
      error => {
        this.errorMessage = error
      }
    );

    this.artworkService.getFilteredArtwork(this.tagId).subscribe(
      response => {
        this.filteredArtwork = response.artworks;
        this.sessionService.setArtworks(response.artworks);
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
