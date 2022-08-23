import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IChat, NewChat } from '../chat.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IChat for edit and NewChatFormGroupInput for create.
 */
type ChatFormGroupInput = IChat | PartialWithRequiredKeyOf<NewChat>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IChat | NewChat> = Omit<T, 'chatDate'> & {
  chatDate?: string | null;
};

type ChatFormRawValue = FormValueOf<IChat>;

type NewChatFormRawValue = FormValueOf<NewChat>;

type ChatFormDefaults = Pick<NewChat, 'id' | 'chatDate' | 'users'>;

type ChatFormGroupContent = {
  id: FormControl<ChatFormRawValue['id'] | NewChat['id']>;
  chatDate: FormControl<ChatFormRawValue['chatDate']>;
  textMessage: FormControl<ChatFormRawValue['textMessage']>;
  users: FormControl<ChatFormRawValue['users']>;
};

export type ChatFormGroup = FormGroup<ChatFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ChatFormService {
  createChatFormGroup(chat: ChatFormGroupInput = { id: null }): ChatFormGroup {
    const chatRawValue = this.convertChatToChatRawValue({
      ...this.getFormDefaults(),
      ...chat,
    });
    return new FormGroup<ChatFormGroupContent>({
      id: new FormControl(
        { value: chatRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      chatDate: new FormControl(chatRawValue.chatDate, {
        validators: [Validators.required],
      }),
      textMessage: new FormControl(chatRawValue.textMessage, {
        validators: [Validators.required],
      }),
      users: new FormControl(chatRawValue.users ?? []),
    });
  }

  getChat(form: ChatFormGroup): IChat | NewChat {
    return this.convertChatRawValueToChat(form.getRawValue() as ChatFormRawValue | NewChatFormRawValue);
  }

  resetForm(form: ChatFormGroup, chat: ChatFormGroupInput): void {
    const chatRawValue = this.convertChatToChatRawValue({ ...this.getFormDefaults(), ...chat });
    form.reset(
      {
        ...chatRawValue,
        id: { value: chatRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ChatFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      chatDate: currentTime,
      users: [],
    };
  }

  private convertChatRawValueToChat(rawChat: ChatFormRawValue | NewChatFormRawValue): IChat | NewChat {
    return {
      ...rawChat,
      chatDate: dayjs(rawChat.chatDate, DATE_TIME_FORMAT),
    };
  }

  private convertChatToChatRawValue(
    chat: IChat | (Partial<NewChat> & ChatFormDefaults)
  ): ChatFormRawValue | PartialWithRequiredKeyOf<NewChatFormRawValue> {
    return {
      ...chat,
      chatDate: chat.chatDate ? chat.chatDate.format(DATE_TIME_FORMAT) : undefined,
      users: chat.users ?? [],
    };
  }
}
