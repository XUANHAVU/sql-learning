import {Component, OnInit, Input} from '@angular/core';
import {AccountService, JhiLanguageHelper, LoginModalService, LoginService} from 'app/core';
import {JhiAlertService, JhiEventManager, JhiLanguageService} from 'ng-jhipster';
import {SessionStorageService} from 'ngx-webstorage';
import {ProfileService} from 'app/layouts';
import {Router} from '@angular/router';
import {VERSION} from 'app/app.constants';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {ICategoryType} from 'app/shared/model/category-type.model';
import {CategoryTypeService} from 'app/entities/category-type';
import {filter, map} from 'rxjs/operators';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs';
import {FlatTreeControl} from '@angular/cdk/tree';
import {MatTreeFlatDataSource, MatTreeFlattener} from '@angular/material/tree';
import {ICategory} from 'app/shared/model/category.model';
import {DataService} from 'app/layouts/data.service';
import {CategoryService} from 'app/entities/category';

/** Flat node with expandable and level information */
interface ExampleFlatNode {
    id: number;
    name: string;
    title: string;
    description: string;
    table: string;
    level: number;
    expandable: boolean;
}

class MyDataNode {
    id: number;
    name: string;
    title: string;
    description: string;
    table: string;
    children?: MyDataNode[];
}

@Component({
    selector: 'jhi-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {
    // @ViewChild('sidenav') sidenav: ElementRef;

    categories: ICategory[];
    categoryTypes: ICategoryType[];
    currentAccount: any;
    eventSubscriber: Subscription;
    clicked: boolean;
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    globalreplace: any;
    globalreplace2: any;

    private transformer = (node: MyDataNode, level: number) => {
        return {
            expandable: !!node.children && node.children.length > 0,
            id: node.id,
            name: node.name,
            title: node.title,
            description: node.description,
            table: node.table,
            level,
        };
    };

    treeControl = new FlatTreeControl<ExampleFlatNode>(
        node => node.level, node => node.expandable);

    treeFlattener = new MatTreeFlattener(
        this.transformer,
        node => node.level,
        node => node.expandable,
        node => node.children);

    dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

    constructor(
        protected categoryTypeService: CategoryTypeService,
        protected categoryService: CategoryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private sessionStorage: SessionStorageService,
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private data: DataService
    ) {
        this.clicked = this.clicked === undefined ? false : true;
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        this.categoryService
            .query()
            .pipe(
                filter((res: HttpResponse<ICategory[]>) => res.ok),
                map((res: HttpResponse<ICategory[]>) => res.body)
            )
            .subscribe(
                (res: ICategory[]) => {
                    this.categories = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.categoryTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<ICategoryType[]>) => res.ok),
                map((res: HttpResponse<ICategoryType[]>) => res.body)
            )
            .subscribe(
                (res: ICategoryType[]) => {
                    this.categoryTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.loadAll();
        this.globalreplace = / /g;
        this.globalreplace2 = /,/g;
    }

    hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

    loadAll() {
        this.categoryTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<ICategoryType[]>) => res.ok),
                map((res: HttpResponse<ICategoryType[]>) => res.body)
            )
            .subscribe(
                (res: ICategoryType[]) => {
                    const tmpArr: MyDataNode[] = [];
                    res.forEach(value => {
                        const tmp = new MyDataNode();
                        tmp.id = value.id;
                        tmp.name = value.nameCategoryType;
                        tmp.children = [];
                        if (value.categoryTypeIDS.length > 0) {
                            value.categoryTypeIDS.forEach(value1 => {
                                tmp.children.push
                                ({
                                    id: value1.id,
                                    name: value1.nameCategory,
                                    title: value1.title,
                                    description: value1.description,
                                    table: value1.nameTableData,
                                    children: []
                                });
                            });
                        }
                        tmpArr.push(tmp);
                    });
                    this.dataSource.data = tmpArr;
                },
                // (res: HttpErrorResponse) => this.onError(res.message)
                (res: HttpErrorResponse) => console.log('Something went wrong!')
            );
    }

    ngOnInit() {

        // let cateType = this.categoryTypeService
        //     .query()
        //     .pipe(
        //         filter((res: HttpResponse<ICategoryType[]>) => res.ok),
        //         map((res: HttpResponse<ICategoryType[]>) => res.body)
        //     );
        //
        // let cate = this.categoryService
        //     .query()
        //     .pipe(
        //         filter((res: HttpResponse<ICategory[]>) => res.ok),
        //         map((res: HttpResponse<ICategory[]>) => res.body)
        //     );
        //
        // forkJoin([cateType, cate]).subscribe(results => {
        //     this.categoryTypes = results[0];
        //     this.categories = results[1];
        //
        //     console.log(this.categoryTypes);
        //     console.log(this.categories);
        // });

        // this.loadAll();

        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
    }

    changeNode(id) {
        this.data.changeNode(id);
    }

    registerChangeInCategoryTypes() {
        this.eventSubscriber = this.eventManager.subscribe('categoryTypeListModification', response => this.loadAll());
    }

    trackId(index: number, item: ICategoryType) {
        return item.id;
    }

    onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    setClicked(val: boolean): void {
        this.clicked = val;
    }

    changeLanguage(languageKey: string) {
        this.sessionStorage.store('locale', languageKey);
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
    }

}
