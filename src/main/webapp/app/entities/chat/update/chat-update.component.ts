import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ChatFormService, ChatFormGroup } from './chat-form.service';
import { IChat } from '../chat.model';
import { ChatService } from '../service/chat.service';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/service/user-extra.service';

@Component({
  selector: 'jhi-chat-update',
  templateUrl: './chat-update.component.html',
})
export class ChatUpdateComponent implements OnInit {
  isSaving = false;
  chat: IChat | null = null;

  userExtrasSharedCollection: IUserExtra[] = [];

  editForm: ChatFormGroup = this.chatFormService.createChatFormGroup();

  constructor(
    protected chatService: ChatService,
    protected chatFormService: ChatFormService,
    protected userExtraService: UserExtraService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUserExtra = (o1: IUserExtra | null, o2: IUserExtra | null): boolean => this.userExtraService.compareUserExtra(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chat }) => {
      this.chat = chat;
      if (chat) {
        this.updateForm(chat);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chat = this.chatFormService.getChat(this.editForm);
    if (chat.id !== null) {
      this.subscribeToSaveResponse(this.chatService.update(chat));
    } else {
      this.subscribeToSaveResponse(this.chatService.create(chat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChat>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(chat: IChat): void {
    this.chat = chat;
    this.chatFormService.resetForm(this.editForm, chat);

    this.userExtrasSharedCollection = this.userExtraService.addUserExtraToCollectionIfMissing<IUserExtra>(
      this.userExtrasSharedCollection,
      ...(chat.users ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userExtraService
      .query()
      .pipe(map((res: HttpResponse<IUserExtra[]>) => res.body ?? []))
      .pipe(
        map((userExtras: IUserExtra[]) =>
          this.userExtraService.addUserExtraToCollectionIfMissing<IUserExtra>(userExtras, ...(this.chat?.users ?? []))
        )
      )
      .subscribe((userExtras: IUserExtra[]) => (this.userExtrasSharedCollection = userExtras));
  }
}
