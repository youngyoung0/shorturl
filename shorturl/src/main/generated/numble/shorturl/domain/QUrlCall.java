package numble.shorturl.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUrlCall is a Querydsl query type for UrlCall
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUrlCall extends EntityPathBase<UrlCall> {

    private static final long serialVersionUID = -133858779L;

    public static final QUrlCall urlCall = new QUrlCall("urlCall");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final EnumPath<Browser> browser = createEnum("browser", Browser.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QUrlCall(String variable) {
        super(UrlCall.class, forVariable(variable));
    }

    public QUrlCall(Path<? extends UrlCall> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUrlCall(PathMetadata metadata) {
        super(UrlCall.class, metadata);
    }

}

