import { Component, OnInit, Renderer2 } from '@angular/core';
import { NgbAccordionConfig } from '@ng-bootstrap/ng-bootstrap';
import * as Rellax from 'rellax';

import {NavigationEnd} from '@angular/router';
import {Router} from '@angular/router';

@Component({
    selector: 'app-components',
    templateUrl: './landing.component.html',
    styleUrls: ['./landing.component.css']
})

export class LandingComponent implements OnInit  {

    navigationSubscription;
    constructor( private renderer : Renderer2, config: NgbAccordionConfig,
        private router : Router) {
        config.closeOthers = true;
        config.type = 'info';

    }

    ngOnInit() {
      var rellaxHeader = new Rellax('.rellax-header');
    }
}
