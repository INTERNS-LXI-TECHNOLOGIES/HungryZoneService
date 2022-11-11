import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-extra',
        data: { pageTitle: 'UserExtras' },
        loadChildren: () => import('./user-extra/user-extra.module').then(m => m.UserExtraModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'Categories' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'food',
        data: { pageTitle: 'Foods' },
        loadChildren: () => import('./food/food.module').then(m => m.FoodModule),
      },
      {
        path: 'order',
        data: { pageTitle: 'Orders' },
        loadChildren: () => import('./order/order.module').then(m => m.OrderModule),
      },
      {
        path: 'cart',
        data: { pageTitle: 'Carts' },
        loadChildren: () => import('./cart/cart.module').then(m => m.CartModule),
      },
      {
        path: 'cart-item',
        data: { pageTitle: 'CartItems' },
        loadChildren: () => import('./cart-item/cart-item.module').then(m => m.CartItemModule),
      },
      {
        path: 'food-item',
        data: { pageTitle: 'FoodItems' },
        loadChildren: () => import('./food-item/food-item.module').then(m => m.FoodItemModule),
      },
      {
        path: 'message',
        data: { pageTitle: 'Messages' },
        loadChildren: () => import('./message/message.module').then(m => m.MessageModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
