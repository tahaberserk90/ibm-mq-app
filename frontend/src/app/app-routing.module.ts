import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MessageListComponent } from './components/message-list/message-list.component';
import { PartnerListComponent } from './components/partner-list/partner-list.component';

const routes: Routes = [
  { path: 'messages', component: MessageListComponent },
  { path: 'partners', component: PartnerListComponent },
  { path: '', redirectTo: '/messages', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }