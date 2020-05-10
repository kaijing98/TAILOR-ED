import { Component, OnInit, ElementRef } from '@angular/core';

import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { SessionServiceService } from '../../services/session-service.service';
import { CustomerServiceService } from '../../services/customer-service.service';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';
import {Customer} from '../../class/customer';
import {Tag} from "../../class/tag";

import {NavigationEnd} from '@angular/router';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: []
})
export class NavbarComponent implements OnInit {
    private toggleButton: any;
    private sidebarVisible: boolean;
    username: string;
    userId: number;
    submitted: boolean;
    customer: Customer;
    customerToUpdate: Customer;

    // tags is the list of all tags in the db
    tags: Tag[];

    selectedTags: Boolean[];
    preferences: Tag[];
    currentTag: Tag;

    infoMessage: string;
    errorMessage: string;

    navigationSubscription;

    reloadCounter : number;

    constructor(public location: Location,
                private router: Router,
                private element: ElementRef,
                private sessionService: SessionServiceService,
                private modalService: NgbModal,
                private customerService: CustomerServiceService) {
        this.sidebarVisible = false;
        this.customerToUpdate = new Customer();
        this.selectedTags = new Array<Boolean>();
        
        this.customer = this.sessionService.getCustomer();
        if(this.customer != null){
            this.username = this.sessionService.getCustomer().username;
            this.customer = this.sessionService.getCustomer();
            this.userId = this.sessionService.getCustomer().userId;
            this.ngOnInit();
            
        }
        console.log("Navigation Bar reload");
        this.navigationSubscription = this.router.events.subscribe((e:any) => {

            window.location.reload;
        
        });
        
    }

    open(content) {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', scrollable: true});
    }

    ngOnInit() {
        const navbar: HTMLElement = this.element.nativeElement;
        this.toggleButton = navbar.getElementsByClassName('navbar-toggler')[0];
        this.username = this.sessionService.getCustomer().username;
        this.customer = this.sessionService.getCustomer();
        this.userId = this.sessionService.getCustomer().userId;

        this.customerService.getAllTags().subscribe(
            response => {
                this.tags = response.tags
            },
            error => {
                this.errorMessage = error
            }
        );

        console.log("Check if it reaches here");
    }

 

    logoutCustomer() {
        this.sessionService.setCustomer(null);
        window.location.reload();
    }

    deleteCustomer() {
        this.customerService.deleteCustomer(this.userId).subscribe(
            response => {
                this.infoMessage = 'Customer deleted ';
                this.errorMessage = null;
                this.sessionService.setCustomer(null);
                window.location.reload();
            },
            error => {
                this.infoMessage = null;
                this.errorMessage = error;
            }
        )
    }

    sidebarClose() {
        const html = document.getElementsByTagName('html')[0];
        // console.log(html);
        this.toggleButton.classList.remove('toggled');
        this.sidebarVisible = false;
        html.classList.remove('nav-open');
    };

    updateCustomer(updateCustomerForm: NgForm) {
        this.submitted = true;

        if (updateCustomerForm.valid) {
            this.customerToUpdate.username = this.username;
            this.customerToUpdate.userId = this.customer.userId;
            this.customerService.updateCustomer(this.customerToUpdate).subscribe(
                response => {
                    this.infoMessage = response.customerToUpdate.firstName + ' user updated!';
                    this.errorMessage = null;
                    this.sessionService.setCustomer(response.customerToUpdate);
                    window.location.reload();
                },
                error => {
                    this.infoMessage = null;
                    this.errorMessage = error;
                }
            )
            this.modalService.dismissAll();
            // this is ugly af :rip: ;-;
            this.modalService.open('User ' + this.customer.username + ' is updated! ', {ariaLabelledBy: 'modal-basic-title'});
        }
    }

    updateCustomerPreferences(updatePreferencesForm: NgForm) {
        this.submitted = true;
        this.preferences = new Array<Tag>();
        for(let i = 0; i < this.selectedTags.length; i++)
        {
            if (this.selectedTags[i]) {
                this.currentTag = new Tag();
                this.currentTag.tagId = this.tags[i].tagId;
                this.currentTag.tagName = this.tags[i].tagName;
                this.preferences[i] = this.currentTag;
            }
        }

        if (updatePreferencesForm.valid) {
            this.customerToUpdate.username = this.username;
            this.customerToUpdate.userId = this.customer.userId;
            this.customerService.updatePreferences(this.customerToUpdate, this.preferences).subscribe(
                response => {
                    this.infoMessage = response.customerToUpdate.firstName + ' user updated!';
                    this.errorMessage = null;
                    this.sessionService.setCustomer(response.customerToUpdate);
                    window.location.reload();
                },
                error => {
                    this.infoMessage = null;
                    this.errorMessage = error;
                }
            )
            this.modalService.dismissAll();
            // this is ugly af :rip: ;-;
            this.modalService.open('User ' + this.customer.username + ' is updated! ', {ariaLabelledBy: 'modal-basic-title'});
        }
    }
}
