package net.itarray.automotion.reporting;

import com.webfirmframework.wffweb.tag.html.Body;
import com.webfirmframework.wffweb.tag.html.Html;
import com.webfirmframework.wffweb.tag.html.TitleTag;
import com.webfirmframework.wffweb.tag.html.attribute.Href;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.links.A;
import com.webfirmframework.wffweb.tag.html.lists.Li;
import com.webfirmframework.wffweb.tag.html.lists.Ol;
import com.webfirmframework.wffweb.tag.html.metainfo.Head;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "automotion")
public class FinalReportBuilder extends AbstractMojo {

    private static final String TARGET_AUTOMOTION = "target/automotion/";
    private static final String TARGET_AUTOMOTION_HTML = TARGET_AUTOMOTION + "html/";

    @Override
    public void execute() throws MojoExecutionException {
        File folder = new File(TARGET_AUTOMOTION_HTML);
        File[] listOfFiles = folder.listFiles();
        List<String> files = new ArrayList<>();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                files.add(file.getName());
            }
        }

        Html html = buildHtml(files);

        File report = new File(TARGET_AUTOMOTION + "index.html");
        report.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(report);
             BufferedOutputStream bos = new BufferedOutputStream(fos);) {

            html.toOutputStream(bos);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Html buildHtml(final List<String> files) {
        return new Html(null,
                new Style("background-color: #fff")) {{
                super.setPrependDocType(true);
                new Head(this) {{
                        new TitleTag(this) {{
                            new NoTag(this, "Automotion report");
                        }};
                        new NoTag(this, "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
                        new NoTag(this, "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">");

                        new NoTag(this, "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>");
                        new NoTag(this, "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
                        new NoTag(this, "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">");
                        new NoTag(this, "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>");
                    }};

                new Body(this) {{
                        new Ol(this) {{
                                for (final String s: files) {

                                    new Li(this) {{

                                            new A(this,
                                                    new Href("html/" + s)) {{
                                                        new NoTag(this, s);
                                            }};
                                        }};
                                }
                            }};
                    }};
            }};
    }
}
