import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

import { CustomerServiceService } from '../../services/customer-service.service';
import { SessionServiceService } from '../../services/session-service.service';
import { Customer } from '../../class/customer';
import { Tag } from '../../class/tag';

interface Alert {
    type: string;
    message: string;
}

@Component({
    selector: 'app-register-customer',
    templateUrl: './register-customer.component.html',
    styleUrls: ['./register-customer.component.css']
})
export class RegisterCustomerComponent implements OnInit {
    alert: Alert;
    submitted: boolean;
    customer: Customer;
    // tags is the list of all tags in the db
    tags: Tag[];

    selectedTags: Boolean[];
    preferences: Tag[];
    currentTag: Tag;

    newCustomer: Customer;
    username: string;
    password: string;

    infoMessage: string;
    errorMessage: string;
    newUserCreatedMsg: string;
    closeResult = '';

    constructor(private router: Router,
                private activatedRoute: ActivatedRoute,
                private customerService: CustomerServiceService,
                private sessionService: SessionServiceService,
                private modalService: NgbModal)
    {
        this.submitted = false;
        this.customer = new Customer();
        this.newCustomer = new Customer();
        this.newUserCreatedMsg = null;
        this.selectedTags = new Array<Boolean>();
    }

    open(content) {
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', scrollable: true});
    }


    ngOnInit() {
        this.customerService.getAllTags().subscribe(
            response => {
                this.tags = response.tags
            },
            error => {
                this.errorMessage = error
            }
        );
    }

    login(loginForm: NgForm) {
        this.submitted = true;

        if (loginForm.valid) {
            this.username = this.customer.username;
            this.password = this.customer.password;
            this.customerService.customerLogin(this.username, this.password).subscribe(
                response => {
                    this.infoMessage = 'Customer Login Success! Welcome ' + response.customer.firstName;
                    this.errorMessage = null;
                    this.sessionService.setCustomer(response.customer);
                    this.router.navigate(["/index"]).then(
                        ()=> {
                            window.location.reload();
                        }
                    );
                    
                },
                error => {
                    this.infoMessage = null;
                    //do not show the http code, just show the error message
                    this.errorMessage = 'Login Failed! ' + error.toString().split(':')[2];
                }
            )
        }
    }

    createCustomer(createCustomerForm: NgForm) {
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

        if (createCustomerForm.valid) {
            this.customerService.createNewCustomer(this.newCustomer, this.preferences).subscribe(
                response => {
                    this.infoMessage = 'New User Created! Welcome ' + response.newCustomer.firstName;
                    this.errorMessage = null;
                    this.newUserCreatedMsg = 'New User Created! Please Login';
                },
                error => {
                    this.infoMessage = null;
                    this.errorMessage = error;
                }
            )
            this.modalService.dismissAll();
            // this is ugly af :rip: ;-;
            this.modalService.open('New User Created! Please Login ', {ariaLabelledBy: 'modal-basic-title'});
        }
    }

}
