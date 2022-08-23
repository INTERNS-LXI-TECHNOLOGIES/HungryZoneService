import { IUser } from 'app/entities/user/user.model';
import { IChat } from 'app/entities/chat/chat.model';

export interface IUserExtra {
  id: number;
  phoneNumber?: string | null;
  address?: string | null;
  locationAtXAxis?: string | null;
  locationAtYAxis?: string | null;
  user?: Pick<IUser, 'id'> | null;
  chats?: Pick<IChat, 'id'>[] | null;
}

export type NewUserExtra = Omit<IUserExtra, 'id'> & { id: null };
