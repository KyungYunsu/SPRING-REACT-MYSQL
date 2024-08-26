import { CommentListItem } from 'types/interface';
import responseDto from '../response.dto';

export default interface GetCommentListResponseDto extends responseDto {
    commentList: CommentListItem[];
    
}