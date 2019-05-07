import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'category-type',
                loadChildren: './category-type/category-type.module#LearnSqlCategoryTypeModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#LearnSqlCategoryModule'
            },
            {
                path: 'content',
                loadChildren: './content/content.module#LearnSqlContentModule'
            },
            {
                path: 'sql-query',
                loadChildren: './sql-query/sql-query.module#LearnSqlSQLQueryModule'
            },
            {
                path: 'type-content',
                loadChildren: './type-content/type-content.module#LearnSqlTypeContentModule'
            },
            {
                path: 'exercises',
                loadChildren: './exercises/exercises.module#LearnSqlExercisesModule'
            },
            {
                path: 'exercises-answer',
                loadChildren: './exercises-answer/exercises-answer.module#LearnSqlExercisesAnswerModule'
            },
            {
                path: 'orders',
                loadChildren: './orders/orders.module#LearnSqlOrdersModule'
            },
            {
                path: 'customer',
                loadChildren: './customer/customer.module#LearnSqlCustomerModule'
            },
            {
                path: 'employees',
                loadChildren: './employees/employees.module#LearnSqlEmployeesModule'
            },
            {
                path: 'shipper',
                loadChildren: './shipper/shipper.module#LearnSqlShipperModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LearnSqlEntityModule {}
