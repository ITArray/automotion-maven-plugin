package net.itarray.automotion.reporting;

import com.webfirmframework.wffweb.tag.html.Body;
import com.webfirmframework.wffweb.tag.html.Html;
import com.webfirmframework.wffweb.tag.html.TitleTag;
import com.webfirmframework.wffweb.tag.html.attribute.Href;
import com.webfirmframework.wffweb.tag.html.attribute.Src;
import com.webfirmframework.wffweb.tag.html.attribute.Target;
import com.webfirmframework.wffweb.tag.html.attribute.global.ClassAttribute;
import com.webfirmframework.wffweb.tag.html.attribute.global.Style;
import com.webfirmframework.wffweb.tag.html.frames.IFrame;
import com.webfirmframework.wffweb.tag.html.links.A;
import com.webfirmframework.wffweb.tag.html.metainfo.Head;
import com.webfirmframework.wffweb.tag.html.programming.Script;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.Div;
import com.webfirmframework.wffweb.tag.html.stylesandsemantics.StyleTag;
import com.webfirmframework.wffweb.tag.htmlwff.NoTag;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "automotion-report")
public class FinalReportBuilder extends AbstractMojo {

    private static final String TARGET_AUTOMOTION = "target/automotion/";
    private static final String TARGET_AUTOMOTION_HTML = TARGET_AUTOMOTION + "html/";

    @Override
    public void execute() {
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
                new Style("background-color: #F5F5F5")) {{
            super.setPrependDocType(true);
            new Head(this) {{
                new TitleTag(this) {{
                    new NoTag(this, "Automotion report");
                }};
                new StyleTag(this) {{
                    new NoTag(this, ".accordion {\n" +
                            "    background-color: #eee;\n" +
                            "    color: #444;\n" +
                            "    cursor: pointer;\n" +
                            "    padding: 18px;\n" +
                            "    width: 100%;\n" +
                            "    border: none;\n" +
                            "    text-align: left;\n" +
                            "    outline: none;\n" +
                            "    font-size: 15px;\n" +
                            "    transition: 0.4s;\n" +
                            "}\n" +
                            "\n" +
                            ".active, .accordion:hover {\n" +
                            "    background-color: #ccc; \n" +
                            "}\n" +
                            "\n" +
                            ".panel {\n" +
                            "    padding: 0;\n" +
                            "    min-height: 500px;\n" +
                            "    display: none;\n" +
                            "    background-color: white;\n" +
                            "}");
                }};
                new NoTag(this, "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
                new NoTag(this, "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">");

                new NoTag(this, "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>");
                new NoTag(this, "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
                new NoTag(this, "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">");
                new NoTag(this, "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>");
            }};

            new Body(this,
                    new Style("background-color: #F5F5F5")) {{
                new Div(this, new ClassAttribute("container-fluid")) {{
                    for (final String s : files) {
                        new Div(this,
                                new ClassAttribute("row"),
                                new Style("background-color: #fff; padding: 10px; margin-top: 3px")) {{
                            new Div(this,
                                    new ClassAttribute("accordion"),
                                    new Style("width: auto; color: #4d4d4d; text-align:left; font-size: 28px; font-weight: 300")) {{
                                new NoTag(this, s);
                                new A(this,
                                        new Style("float: right"),
                                        new ClassAttribute("btn btn-info"),
                                        new Href("html/" + s),
                                        new Target("target")
                                        ){{
                                            new NoTag(this, "Fullscreen");
                                }};
                            }};

                            new Div(this,
                                    //new Style("background: #f5f5f5"),
                                    new ClassAttribute("panel")) {{
                                        new IFrame(this,
                                                new Style("width: 100%; height: 1000px"),
                                                new Src("html/" + s));

                            }};
                        }};
                    }
                }};

                new Script(this) {{
                    new NoTag(this, "var acc = document.getElementsByClassName(\"accordion\");\n" +
                            "var i;\n" +
                            "\n" +
                            "for (i = 0; i < acc.length; i++) {\n" +
                            "    acc[i].addEventListener(\"click\", function() {\n" +
                            "        this.classList.toggle(\"active\");\n" +
                            "        var panel = this.nextElementSibling;\n" +
                            "        if (panel.style.display === \"block\") {\n" +
                            "            panel.style.display = \"none\";\n" +
                            "        } else {\n" +
                            "            panel.style.display = \"block\";\n" +
                            "        }\n" +
                            "    });\n" +
                            "}");
                }};
            }};
        }};
    }
}
